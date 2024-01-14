package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
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
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return TypeSettlementResponse
                .builder()
                .id(response.id())
                .uaName("село")
                .enName("other")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (TypeSettlementResponse) expectedResponse;
        return TypeSettlementRequest
                .builder()
                .uaName("")
                .enName(response.enName())
                .build();
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
