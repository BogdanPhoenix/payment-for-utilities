package org.university.payment_for_utilities.services.implementations.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.responses.company.CompanyPhoneNumResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(CompanyEntitiesRequestTestContextConfiguration.class)
class CompanyPhoneNumServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("companyRivneOblenergoPhoneNumRequest")
    private CompanyPhoneNumRequest companyPhoneNumRequest;
    @Autowired
    @Qualifier("companyKyivOblenergoPhoneNumRequest")
    private CompanyPhoneNumRequest companyKyivOblenergoPhoneNumRequest;
    @Autowired
    @Qualifier("updateBankPhoneNum")
    private PhoneNum updateBankPhoneNum;

    @Autowired
    public CompanyPhoneNumServiceTest(CompanyPhoneNumService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = companyPhoneNumRequest;
        secondRequest = companyKyivOblenergoPhoneNumRequest;
        emptyRequest = CompanyPhoneNumRequest
                .empty();

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
                .phoneNum(updateBankPhoneNum)
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
}
