package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.UnitMeasurementRequest;
import org.university.payment_for_utilities.services.implementations.TransliterationServiceTest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.UnitMeasurementService;

@SpringBootTest
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
public class UnitMeasurementServiceTest extends TransliterationServiceTest {
    @Autowired
    @Qualifier("unitKilowattRequest")
    private UnitMeasurementRequest unitKilowattRequest;
    @Autowired
    @Qualifier("unitCubicMeterRequest")
    private UnitMeasurementRequest unitCubicMeterRequest;

    @Autowired
    public UnitMeasurementServiceTest(UnitMeasurementService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = unitKilowattRequest;
        secondRequest = unitCubicMeterRequest;
        emptyRequest = UnitMeasurementRequest
                .empty();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = UnitMeasurementRequest
                .builder()
                .uaName("")
                .enName("new data")
                .build();

        testUpdateValueCorrectWithOneChangedParameter(newValue);
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    @Override
    protected void testAddValueThrowInvalidInputData() {
        var withNum = UnitMeasurementRequest
                .builder()
                .uaName("кілова4т")
                .enName("kilo5watt")
                .build();

        var withSpecialCharacter = UnitMeasurementRequest
                .builder()
                .uaName("кілова$т")
                .enName("kilow@att")
                .build();

        testAddValueThrowInvalidInputData(withNum, withSpecialCharacter);
    }

    @Test
    @DisplayName("Check for exceptions when data was transferred in an incorrect format in an update request.")
    @Override
    protected void testUpdateValueThrowInvalidInputData() {
        var incorrectRequest = UnitMeasurementRequest
                .builder()
                .uaName("кілова4т")
                .enName("kilowatt")
                .build();

        testUpdateValueThrowInvalidInputData(incorrectRequest);
    }
}
