package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.pojo.update_request.address.OblastUpdateRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class OblastServiceTest extends TransliterationServiceTest {
    @Autowired
    @Qualifier("oblastRivneRequest")
    private OblastRequest rivneRequest;
    @Autowired
    @Qualifier("oblastKyivRequest")
    private OblastRequest kyivRequest;

    @Autowired
    public OblastServiceTest(OblastService service) {
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = rivneRequest;
        secondRequest = kyivRequest;

        emptyRequest = OblastRequest
                .builder()
                .uaName("")
                .build();

        correctUpdateRequest = OblastUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData() {
        var withNum = OblastRequest
                .builder()
                .uaName("Рівн5енська")
                .enName("Rivnenska")
                .build();
        var withSpecialCharacter = OblastRequest
                .builder()
                .uaName("Рівн@енська")
                .enName("Rivnenska")
                .build();

        testAddValueThrowInvalidInputData(withNum, withSpecialCharacter);
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter(){
        var otherName = "other";

        var updateRequest = OblastUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(OblastRequest
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
        var emptyUpdateRequest = OblastUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = OblastUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = OblastUpdateRequest
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
        var incorrectRequest = OblastRequest
                .builder()
                .uaName("Рівне5нська")
                .enName("Rivne")
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
