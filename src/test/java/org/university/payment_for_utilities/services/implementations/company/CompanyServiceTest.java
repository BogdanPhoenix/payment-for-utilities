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
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.CompanyService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(CompanyEntitiesRequestTestContextConfiguration.class)
class CompanyServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("companyRequest")
    private CompanyRequest companyRequest;

    @Autowired
    @Qualifier("addressKyivResidence")
    private AddressResidence addressResidence;
    @Autowired
    @Qualifier("kyivEdrpou")
    private Edrpou edrpou;
    @Autowired
    @Qualifier("kyivWebsite")
    private Website website;

    @Autowired
    public CompanyServiceTest(CompanyService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = companyRequest;

        emptyRequest = CompanyRequest
                .empty();

        secondRequest = CompanyRequest
                .builder()
                .address(addressResidence)
                .edrpou(edrpou)
                .website(website)
                .name("Київ ОблЕнерго")
                .currentAccount("14236589632145")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = CompanyRequest
                .builder()
                .address(AddressResidence.empty())
                .edrpou(Edrpou.empty())
                .website(Website.empty())
                .name("Київ-обленерго")
                .currentAccount("72341000245521")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (CompanyResponse) service.addValue(secondRequest);
        var updateResponse = (CompanyResponse) service.updateValue(updateRequest);

        assertEquals(response.id(), updateResponse.id());
        assertEquals(newValue.name(), updateResponse.name());
        assertEquals(response.address(), updateResponse.address());
        assertEquals(response.edrpou(), updateResponse.edrpou());
        assertEquals(response.website(), updateResponse.website());
        assertEquals(newValue.currentAccount(), updateResponse.currentAccount());
    }

    @ParameterizedTest
    @MethodSource("testPhoneCurrentAccounts")
    @DisplayName("Check the exceptions if the request has an incorrect current account format.")
    void testCurrentAccountThrowInvalidInputDataException(String numAccount){
        var companyRequest = (CompanyRequest) firstRequest;
        var request = CompanyRequest
                .builder()
                .address(companyRequest.address())
                .edrpou(companyRequest.edrpou())
                .website(companyRequest.website())
                .name(companyRequest.name())
                .currentAccount(numAccount)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneCurrentAccounts(){
        return Stream.of(
                Arguments.of("4236895213g575"),
                Arguments.of("752139$7512452"),
                Arguments.of("452136"),
                Arguments.of("145236985123654786")
        );
    }
}
