package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class SettlementNameServiceTest extends TransliterationServiceTest {
    @Autowired
    @Qualifier("nameRivneRequest")
    private SettlementNameRequest rivneRequest;
    @Autowired
    @Qualifier("nameKyivRequest")
    private SettlementNameRequest kyivRequest;

    @Autowired
    public SettlementNameServiceTest(SettlementNameService service){
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = rivneRequest;
        secondRequest = kyivRequest;

        emptyRequest = SettlementNameRequest
                .empty();

        super.initRequest();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData(){
        var withNum = SettlementNameRequest
                .builder()
                .uaName("Грин9івка")
                .enName("Hrynivka")
                .build();

        var withSpecialCharacter = SettlementNameRequest
                .builder()
                .uaName("Гринівка")
                .enName("Hry^nivka")
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
                .newValue(SettlementNameRequest
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
        var incorrectRequest = SettlementNameRequest
                .builder()
                .uaName("Грин9івка")
                .enName("Hrynivka")
                .build();

        testUpdateValueThrowInvalidInputData(incorrectRequest);
    }
}
