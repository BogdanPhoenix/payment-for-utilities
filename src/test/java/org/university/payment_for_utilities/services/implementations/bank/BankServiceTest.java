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
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.update_request.bank.BankUpdateRequest;
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
    public BankServiceTest(BankService service){ this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = privateBankRequest;

        emptyRequest = BankRequest
                .builder()
                .build();

        secondRequest = BankRequest
                .builder()
                .name("Raiffeisen Bank")
                .webSite("https://raiffeisen.ua/")
                .edrpou("14305909")
                .mfo("380805")
                .build();

        correctUpdateRequest = BankUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = BankRequest
                .builder()
                .name("Private Bank")
                .webSite("")
                .edrpou("14380701")
                .mfo("")
                .build();

        var updateRequest = BankUpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (BankResponse) service.addValue(secondRequest);
        var updateResponse = (BankResponse) service.updateValue(updateRequest);

        assertEquals(response.getId(), updateResponse.getId());
        assertEquals(newValue.getName(), updateResponse.getName());
        assertEquals(response.getWebSite(), updateResponse.getWebSite());
        assertEquals(newValue.getEdrpou(), updateResponse.getEdrpou());
        assertEquals(response.getMfo(), updateResponse.getMfo());
    }

    @Test
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Override
    protected void testUpdateValueThrowRequestEmpty() {
        var emptyUpdateRequest = BankUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = BankUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(secondRequest)
                .build();

        var requestNewValueEmpty = BankUpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(emptyRequest)
                .build();

        testUpdateValueThrowRequestEmpty(emptyUpdateRequest, requestOldValueEmpty, requestNewValueEmpty);
    }

    @Test
    @DisplayName("Check exceptions when the request has an incorrect format of the bank's website.")
    void testWedSiteThrowInvalidInputDataException(){
        var request = (BankRequest) firstRequest;

        request.setWebSite("privatbank.ua");
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    @ParameterizedTest
    @MethodSource("testPhoneEdrpous")
    @DisplayName("Check for exceptions when the request has an invalid EDRPOU format.")
    void testWedEdrpouThrowInvalidInputDataException(String edrpou){
        var request = (BankRequest) firstRequest;

        request.setEdrpou(edrpou);
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    @ParameterizedTest
    @MethodSource("testPhoneMfos")
    @DisplayName("Check for exceptions when the request has an invalid MFO format.")
    void testWedMfoThrowInvalidInputDataException(String mfo){
        var request = (BankRequest) firstRequest;

        request.setMfo(mfo);
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneEdrpous(){
        return Stream.of(
                Arguments.of("14360b570"),
                Arguments.of("143605%70"),
                Arguments.of("1436"),
                Arguments.of("1436750570")
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
