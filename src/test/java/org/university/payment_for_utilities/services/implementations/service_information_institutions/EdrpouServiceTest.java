package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
class EdrpouServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankEdrpouRequest")
    private EdrpouRequest privateBankEdrpouRequest;
    @Autowired
    @Qualifier("raiffeisenBankEdrpouRequest")
    private EdrpouRequest raiffeisenBankEdrpouRequest;

    @Autowired
    public EdrpouServiceTest(EdrpouService service) { this.service = service;}

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = privateBankEdrpouRequest;
        secondRequest = raiffeisenBankEdrpouRequest;
        emptyRequest = EdrpouRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return EdrpouResponse
                .builder()
                .id(response.id())
                .edrpou("75231456")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (EdrpouResponse) expectedResponse;
        return EdrpouRequest
                .builder()
                .edrpou(response.edrpou())
                .build();
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
