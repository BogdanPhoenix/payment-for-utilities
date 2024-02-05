package org.university.payment_for_utilities.services.implementations.user;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.user.ContractEntityRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;
import org.university.payment_for_utilities.pojo.responses.user.UserResponse;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.user.ContractEntityService;

@SpringBootTest
@Import(UserEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContractEntityServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("createRivneContractRequest")
    private ContractEntityRequest rivneContractRequest;
    @Autowired
    @Qualifier("createKyivContractRequest")
    private ContractEntityRequest kyivContractRequest;
    @Autowired
    @Qualifier("registeredUserOlegResponse")
    private UserResponse registeredUserOlegResponse;
    @Autowired
    @Qualifier("createRivneTariff")
    private CompanyTariffResponse rivneTariff;

    @Autowired
    public ContractEntityServiceTest(ContractEntityService service) { super(service); }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = rivneContractRequest;
        secondRequest = kyivContractRequest;
        emptyRequest = ContractEntityRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return ContractEntityResponse
                .builder()
                .id(response.getId())
                .registeredUser(registeredUserOlegResponse)
                .companyTariff(rivneTariff)
                .numContract("42316895121")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (ContractEntityResponse) expectedResponse;
        return ContractEntityRequest
                .builder()
                .registeredUser(Response.EMPTY_PARENT_ENTITY)
                .companyTariff(response.getCompanyTariff().getId())
                .numContract(response.getNumContract())
                .build();
    }
}
