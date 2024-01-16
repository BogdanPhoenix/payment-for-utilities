package org.university.payment_for_utilities.services.implementations.company;

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
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.company.TypeOfferService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(CompanyEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TypeOfferServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("typeOfferElectricRequest")
    private TypeOfferRequest typeOfferRequest;
    @Autowired
    @Qualifier("typeOfferGasRequest")
    private TypeOfferRequest typeOfferGasRequest;

    @Autowired
    public TypeOfferServiceTest(TypeOfferService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = typeOfferRequest;
        secondRequest = typeOfferGasRequest;

        emptyRequest = TypeOfferRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return TypeOfferResponse
                .builder()
                .id(response.id())
                .unitMeasurement(typeOfferGasRequest.unitMeasurement())
                .uaName("Нічний")
                .enName("Night")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (TypeOfferResponse) expectedResponse;
        return TypeOfferRequest
                .builder()
                .unitMeasurement(UnitMeasurement.empty())
                .uaName(response.uaName())
                .enName(response.enName())
                .build();
    }

    @ParameterizedTest
    @MethodSource("testNames")
    @DisplayName("")
    void testNameThrowInvalidInputDataException(String uaName, String enName){
        var typeOfferRequest = (TypeOfferRequest) firstRequest;
        var request = TypeOfferRequest
                .builder()
                .unitMeasurement(typeOfferRequest.unitMeasurement())
                .uaName(uaName)
                .enName(enName)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testNames(){
        return Stream.of(
                Arguments.of("тест", "test4"),
                Arguments.of("тест№", "test")
        );
    }
}
