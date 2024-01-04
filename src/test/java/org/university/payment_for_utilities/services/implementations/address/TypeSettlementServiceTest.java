package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.pojo.update_request.address.TypeSettlementUpdateRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@SpringBootTest
class TypeSettlementServiceTest extends TransliterationServiceTest {
    @Autowired
    public TypeSettlementServiceTest(TypeSettlementService service){
        this.service = service;
        initRequest();
    }

    @Override
    protected void initRequest() {
        emptyRequest = TypeSettlementRequest
                .builder()
                .uaName("")
                .build();

        firstRequest = TypeSettlementRequest
                .builder()
                .uaName("Зброди")
                .enName("Zbrody")
                .build();

        secondRequest = TypeSettlementRequest
                .builder()
                .uaName("Василівка")
                .enName("Vasylivka")
                .build();

        correctUpdateRequest = TypeSettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData(){
        var withNum = TypeSettlementRequest
                .builder()
                .uaName("Збро6ди")
                .enName("Zbrody")
                .build();
        var withSpecialCharacter = TypeSettlementRequest
                .builder()
                .uaName("Зброди")
                .enName("Zbrody@")
                .build();

        testAddValueThrowInvalidInputData(withNum, withSpecialCharacter);
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter(){
        var otherName = "other";

        var updateRequest = TypeSettlementUpdateRequest
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
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Override
    protected void testUpdateValueThrowRequestEmpty(){
        var emptyUpdateRequest = TypeSettlementUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = TypeSettlementUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = TypeSettlementUpdateRequest
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
        var incorrectRequest = TypeSettlementRequest
                .builder()
                .uaName("Збро6ди")
                .enName("Zbrody")
                .build();

        var correctOnlyOldValue = TypeSettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = TypeSettlementUpdateRequest
                .builder()
                .oldValue(incorrectRequest)
                .newValue(firstRequest)
                .build();

        testUpdateValueThrowInvalidInputData(correctOnlyOldValue, correctOnlyNewValue);
    }
}
