package org.university.payment_for_utilities.services.implementations.company;

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
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.company.TypeOfferService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(CompanyEntitiesRequestTestContextConfiguration.class)
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

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var response = service.addValue(secondRequest);
        var expectedResponse = TypeOfferResponse
                .builder()
                .id(response.id())
                .unitMeasurement(typeOfferGasRequest.unitMeasurement())
                .uaName("Нічний")
                .enName("Night")
                .build();

        var newValue = TypeOfferRequest
                .builder()
                .unitMeasurement(UnitMeasurement.empty())
                .uaName(expectedResponse.uaName())
                .enName(expectedResponse.enName())
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var updateResponse = service.updateValue(updateRequest);

        assertThat(updateResponse)
                .isNotEqualTo(response)
                .isEqualTo(expectedResponse);
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
