package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.pojo.update_request.address.OblastUpdateRequest;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.pojo.update_request.address.SettlementNameUpdateRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;

@SpringBootTest
class SettlementNameServiceTest extends TransliterationServiceTest {
    @Autowired
    public SettlementNameServiceTest(SettlementNameService service){
        this.service = service;
        initRequest();
    }

    @Override
    protected void initRequest() {
        emptyRequest = SettlementNameRequest
                .builder()
                .uaName("")
                .build();

        firstRequest = SettlementNameRequest
                .builder()
                .uaName("Гринівка")
                .enName("Hrynivka")
                .build();

        secondRequest = SettlementNameRequest
                .builder()
                .uaName("Липівка")
                .enName("Lypivka")
                .build();

        correctUpdateRequest = SettlementNameUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
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

        var updateRequest = SettlementNameUpdateRequest
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
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Override
    protected void testUpdateValueThrowRequestEmpty(){
        var emptyUpdateRequest = SettlementNameUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = SettlementNameUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = SettlementNameUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(emptyRequest)
                .build();

        testUpdateValueThrowRequestEmpty(emptyUpdateRequest, requestOldValueEmpty, requestNewValueEmpty);
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

        var correctOnlyOldValue = OblastUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = OblastUpdateRequest
                .builder()
                .oldValue(incorrectRequest)
                .newValue(firstRequest)
                .build();

        testUpdateValueThrowInvalidInputData(correctOnlyOldValue, correctOnlyNewValue);
    }
}
