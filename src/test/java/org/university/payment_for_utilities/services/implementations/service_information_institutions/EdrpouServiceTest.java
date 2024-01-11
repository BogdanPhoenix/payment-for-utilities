package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
class EdrpouServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankEdrpouRequest")
    private EdrpouRequest privateBankEdrpouRequest;

    @Autowired
    public EdrpouServiceTest(EdrpouService service) { this.service = service;}

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = privateBankEdrpouRequest;

        emptyRequest = EdrpouRequest
                .empty();

        secondRequest = EdrpouRequest
                .builder()
                .edrpou("14305909")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = EdrpouRequest
                .builder()
                .edrpou("75231456")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (EdrpouResponse) service.addValue(secondRequest);
        var updateResponse = (EdrpouResponse) service.updateValue(updateRequest);

        assertEquals(response.id(), updateResponse.id());
        assertEquals(newValue.edrpou(), updateResponse.edrpou());
    }

    @ParameterizedTest
    @MethodSource("testPhoneEdrpous")
    @DisplayName("Check for exceptions when the request has an invalid EDRPOU format.")
    void testWedEdrpouThrowInvalidInputDataException(String edrpou){
        var request = EdrpouRequest
                .builder()
                .edrpou(edrpou)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneEdrpous(){
        return Stream.of(
                Arguments.of("14360b570"),
                Arguments.of("143605%70"),
                Arguments.of("1436"),
                Arguments.of("1436750570")
        );
    }
}
