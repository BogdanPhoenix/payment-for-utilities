package org.university.payment_for_utilities.services.implementations.bank;

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
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.responses.bank.BankPhoneNumResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(BankEntitiesRequestTestContextConfiguration.class)
class BankPhoneNumTest extends CrudServiceTest {
    @Autowired
    @Qualifier("bankPhoneNumRequest")
    private BankPhoneNumRequest bankPhoneNumRequest;
    @Autowired
    @Qualifier("raiffeisenBankUpdate")
    private Bank raiffeisenBankUpdate;

    @Autowired
    public BankPhoneNumTest(BankPhoneNumService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = bankPhoneNumRequest;
        var bank = bankPhoneNumRequest.getBank();

        emptyRequest = BankPhoneNumRequest
                .builder()
                .build();

        secondRequest = BankPhoneNumRequest
                .builder()
                .bank(bank)
                .phoneNum("380964213564")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = BankPhoneNumRequest
                .builder()
                .bank(raiffeisenBankUpdate)
                .phoneNum("380444521365")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (BankPhoneNumResponse) service.addValue(secondRequest);
        var updateResponse = (BankPhoneNumResponse) service.updateValue(updateRequest);

        assertEquals(response.getId(), updateResponse.getId());
        assertEquals(newValue.getBank(), updateResponse.getBank());
        assertEquals(newValue.getPhoneNum(), updateResponse.getPhoneNum());
    }

    @ParameterizedTest
    @MethodSource("testPhoneNums")
    @DisplayName("Check the exceptions when an invalid mobile phone number format is passed in the request.")
    void testPhoneThrowInvalidInputDataException(String num){
        var request = (BankPhoneNumRequest) firstRequest;

        request.setPhoneNum(num);
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
