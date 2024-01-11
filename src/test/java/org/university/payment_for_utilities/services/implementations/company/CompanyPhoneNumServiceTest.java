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
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.responses.company.CompanyPhoneNumResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(CompanyEntitiesRequestTestContextConfiguration.class)
class CompanyPhoneNumServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("companyPhoneNumRequest")
    private CompanyPhoneNumRequest companyPhoneNumRequest;

    @Autowired
    public CompanyPhoneNumServiceTest(CompanyPhoneNumService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = companyPhoneNumRequest;
        var company = companyPhoneNumRequest.company();

        emptyRequest = CompanyPhoneNumRequest
                .empty();

        secondRequest = CompanyPhoneNumRequest
                .builder()
                .company(company)
                .phoneNum("380421003698")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var response = (CompanyPhoneNumResponse) service.addValue(secondRequest);
        var expectedResponse = CompanyPhoneNumResponse
                .builder()
                .id(response.id())
                .company(response.company())
                .phoneNum("380444961365")
                .build();

        var newValue = CompanyPhoneNumRequest
                .builder()
                .company(Company.empty())
                .phoneNum(expectedResponse.phoneNum())
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var updateResponse = (CompanyPhoneNumResponse) service.updateValue(updateRequest);

        assertThat(updateResponse)
                .isEqualTo(expectedResponse);
    }

    @ParameterizedTest
    @MethodSource("testPhoneNums")
    @DisplayName("Check the exceptions when an invalid mobile phone number format is passed in the request.")
    void testPhoneThrowInvalidInputDataException(String num){
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) firstRequest;
        var request = CompanyPhoneNumRequest
                .builder()
                .company(companyPhoneNumRequest.company())
                .phoneNum(num)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneNums(){
        return Stream.of(
                Arguments.of("380"),
                Arguments.of("380999999999999999"),
                Arguments.of("38044452f1365"),
                Arguments.of("38044452@1365"),
                Arguments.of("574445251365")
        );
    }
}
