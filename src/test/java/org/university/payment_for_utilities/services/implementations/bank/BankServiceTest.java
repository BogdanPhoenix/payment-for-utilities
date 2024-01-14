package org.university.payment_for_utilities.services.implementations.bank;

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
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.bank.BankService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(BankEntitiesRequestTestContextConfiguration.class)
class BankServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankRequest")
    private BankRequest privateBankRequest;
    @Autowired
    @Qualifier("raiffeisenBankRequest")
    private BankRequest raiffeisenBankRequest;

    @Autowired
    public BankServiceTest(BankService service){ this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = privateBankRequest;
        secondRequest = raiffeisenBankRequest;
        emptyRequest = BankRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return BankResponse
                .builder()
                .id(response.id())
                .name("Raiffeisen Bank Ua")
                .website(raiffeisenBankRequest.website())
                .edrpou(raiffeisenBankRequest.edrpou())
                .mfo("423147")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (BankResponse) expectedResponse;
        return BankRequest
                .builder()
                .name(response.name())
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .mfo(response.mfo())
                .build();
    }

    @ParameterizedTest
    @MethodSource("testPhoneMfos")
    @DisplayName("Check for exceptions when the request has an invalid MFO format.")
    void testMfoThrowInvalidInputDataException(String mfo){
        var bankRequest = (BankRequest) firstRequest;
        var request = BankRequest
                .builder()
                .name(bankRequest.name())
                .website(bankRequest.website())
                .edrpou(bankRequest.edrpou())
                .mfo(mfo)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneMfos(){
        return Stream.of(
                Arguments.of("305f299"),
                Arguments.of("305#299"),
                Arguments.of("3099"),
                Arguments.of("30529999")
        );
    }
}
