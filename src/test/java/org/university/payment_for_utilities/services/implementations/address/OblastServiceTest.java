package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
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
                .empty();

        super.initRequest();
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

        var updateRequest = UpdateRequest
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
    @DisplayName("Check for exceptions when data was transferred in an incorrect format in an update request.")
    @Override
    protected void testUpdateValueThrowInvalidInputData(){
        var incorrectRequest = OblastRequest
                .builder()
                .uaName("Рівне5нська")
                .enName("Rivne")
                .build();

        testUpdateValueThrowInvalidInputData(incorrectRequest);
    }
}
