package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class TypeSettlementServiceTest extends TransliterationServiceTest {
    @Autowired
    @Qualifier("typeSettlementCityRequest")
    private TypeSettlementRequest cityRequest;
    @Autowired
    @Qualifier("typeSettlementVillageRequest")
    private TypeSettlementRequest villageRequest;

    @Autowired
    public TypeSettlementServiceTest(TypeSettlementService service){
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = cityRequest;
        secondRequest = villageRequest;

        emptyRequest = TypeSettlementRequest
                .builder()
                .uaName("")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData(){
        var withNum = TypeSettlementRequest
                .builder()
                .uaName("міс6то")
                .enName("city")
                .build();
        var withSpecialCharacter = TypeSettlementRequest
                .builder()
                .uaName("місто")
                .enName("city@")
                .build();

        testAddValueThrowInvalidInputData(withNum, withSpecialCharacter);
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter(){
        var otherName = "other";

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(TypeSettlementRequest
                        .builder()
                        .uaName("")
                        .enName(otherName)
                        .build()
                )
                .build();

        testUpdateValueCorrectWithOneChangedParameter(updateRequest);
    }

    @Test
    @DisplayName("Check for exceptions when data was transferred in an incorrect format in an update request.")
    @Override
    protected void testUpdateValueThrowInvalidInputData(){
        var incorrectRequest = TypeSettlementRequest
                .builder()
                .uaName("міс6то")
                .enName("city")
                .build();

        testUpdateValueThrowInvalidInputData(incorrectRequest);
    }
}
