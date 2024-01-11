package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class DistrictServiceTest extends TransliterationServiceTest {
    @Autowired
    @Qualifier("districtRivneRequest")
    private DistrictRequest rivneRequest;
    @Autowired
    @Qualifier("districtBelotserkivskyiRequest")
    private DistrictRequest belotserkivskyiRequest;

    @Autowired
    public DistrictServiceTest(DistrictService service) {
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = rivneRequest;
        secondRequest = belotserkivskyiRequest;

        emptyRequest = DistrictRequest
                .empty();

        super.initRequest();
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

        var updateRequest = UpdateRequest
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
    @DisplayName("Check for exceptions when data was transferred in an incorrect format in an update request.")
    @Override
    protected void testUpdateValueThrowInvalidInputData(){
        var incorrectRequest = DistrictRequest
                .builder()
                .uaName("Рівне5нський")
                .enName("Rivne")
                .build();

        testUpdateValueThrowInvalidInputData(incorrectRequest);
    }
}
