package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.pojo.update_request.address.DistrictUpdateRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

@SpringBootTest
class DistrictServiceTest extends TransliterationServiceTest {
    @Autowired
    public DistrictServiceTest(DistrictService service) {
        this.service = service;
        initRequest();
    }

    @Override
    protected void initRequest(){
        emptyRequest = DistrictRequest
                .builder()
                .uaName("")
                .build();

        firstRequest = DistrictRequest
                .builder()
                .uaName("Рівненський")
                .enName("Rivne")
                .build();

        secondRequest = DistrictRequest
                .builder()
                .uaName("Білоцерківський")
                .enName("Belotserkivskyi")
                .build();

        correctUpdateRequest = DistrictUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData() {
        var withNum = DistrictRequest
                .builder()
                .uaName("Рівн5енський")
                .enName("Rivne")
                .build();
        var withSpecialCharacter = DistrictRequest
                .builder()
                .uaName("Рівн@енський")
                .enName("Rivne")
                .build();

        testAddValueThrowInvalidInputData(withNum, withSpecialCharacter);
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var otherName = "other";

        var updateRequest = DistrictUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(DistrictRequest
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
        var emptyUpdateRequest = DistrictUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = DistrictUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = DistrictUpdateRequest
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
        var incorrectRequest = DistrictRequest
                .builder()
                .uaName("Рівне5нський")
                .enName("Rivne")
                .build();

        var correctOnlyOldValue = DistrictUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = DistrictUpdateRequest
                .builder()
                .oldValue(incorrectRequest)
                .newValue(firstRequest)
                .build();

        testUpdateValueThrowInvalidInputData(correctOnlyOldValue, correctOnlyNewValue);
    }
}
