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
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.bank.BankService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(BankEntitiesRequestTestContextConfiguration.class)
class BankServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankRequest")
    private BankRequest privateBankRequest;
    @Autowired
    @Qualifier("raiffeisenBankWebsite")
    private Website raiffeisenBankWebsite;
    @Autowired
    @Qualifier("raiffeisenBankUpdateWebsite")
    private Website raiffeisenBankUpdateWebsite;
    @Autowired
    @Qualifier("raiffeisenBankEdrpou")
    private Edrpou raiffeisenBankEdrpou;
    @Autowired
    @Qualifier("raiffeisenBankUpdateEdrpou")
    private Edrpou raiffeisenBankUpdateEdrpou;

    @Autowired
    public BankServiceTest(BankService service){ this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = privateBankRequest;

        emptyRequest = BankRequest
                .empty();

        secondRequest = BankRequest
                .builder()
                .name("Raiffeisen Bank")
                .website(raiffeisenBankWebsite)
                .edrpou(raiffeisenBankEdrpou)
                .mfo("380805")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = BankRequest
                .builder()
                .name("Raiffeisen Bank Ua")
                .website(raiffeisenBankUpdateWebsite)
                .edrpou(raiffeisenBankUpdateEdrpou)
                .mfo("")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (BankResponse) service.addValue(secondRequest);
        var updateResponse = (BankResponse) service.updateValue(updateRequest);

        assertEquals(response.id(), updateResponse.id());
        assertEquals(newValue.name(), updateResponse.name());
        assertEquals(newValue.website(), updateResponse.webSite());
        assertEquals(newValue.edrpou(), updateResponse.edrpou());
        assertEquals(response.mfo(), updateResponse.mfo());
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
