package org.university.payment_for_utilities.services.implementations.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.responses.bank.BankPhoneNumResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(BankEntitiesRequestTestContextConfiguration.class)
class BankPhoneNumTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankPhoneNumRequest")
    private BankPhoneNumRequest privateBankPhoneNumRequest;
    @Autowired
    @Qualifier("raiffeisenBankPhoneNumRequest")
    private BankPhoneNumRequest raiffeisenBankPhoneNumRequest;
    @Autowired
    @Qualifier("updateBankPhoneNum")
    private PhoneNum updateBankPhoneNum;

    @Autowired
    public BankPhoneNumTest(BankPhoneNumService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = privateBankPhoneNumRequest;
        secondRequest = raiffeisenBankPhoneNumRequest;

        emptyRequest = BankPhoneNumRequest
                .empty();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var response = (BankPhoneNumResponse) service.addValue(secondRequest);
        var expectedResponse = BankPhoneNumResponse
                .builder()
                .id(response.id())
                .bank(response.bank())
                .phoneNum(updateBankPhoneNum)
                .build();

        var newValue = BankPhoneNumRequest
                .builder()
                .bank(Bank.empty())
                .phoneNum(expectedResponse.phoneNum())
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var updateResponse = (BankPhoneNumResponse) service.updateValue(updateRequest);

        assertThat(updateResponse)
                .isEqualTo(expectedResponse);
    }
}
