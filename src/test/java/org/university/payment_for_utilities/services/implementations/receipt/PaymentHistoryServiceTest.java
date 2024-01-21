package org.university.payment_for_utilities.services.implementations.receipt;

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
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.PaymentHistoryRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.receipt.PaymentHistoryResponse;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.receipt.PaymentHistoryService;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.university.payment_for_utilities.domains.receipt.PaymentHistory.EMPTY_COUNTER;

@SpringBootTest
@Import(ReceiptEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PaymentHistoryServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("rivnePaymentHistoryRequest")
    private PaymentHistoryRequest rivnePaymentHistoryRequest;
    @Autowired
    @Qualifier("kyivPaymentHistoryRequest")
    private PaymentHistoryRequest kyivPaymentHistoryRequest;

    @Autowired
    public PaymentHistoryServiceTest(PaymentHistoryService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = rivnePaymentHistoryRequest;
        secondRequest = kyivPaymentHistoryRequest;
        emptyRequest = PaymentHistoryRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return PaymentHistoryResponse
                .builder()
                .id(response.getId())
                .receipt(kyivPaymentHistoryRequest.getReceipt())
                .prevValueCounter(kyivPaymentHistoryRequest.getPrevValueCounter())
                .currentValueCounter(1500)
                .finalPaymentAmount(new BigDecimal("500.00"))
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (PaymentHistoryResponse) expectedResponse;
        return PaymentHistoryRequest
                .builder()
                .receipt(Receipt.empty())
                .prevValueCounter(EMPTY_COUNTER)
                .currentValueCounter(response.getCurrentValueCounter())
                .finalPaymentAmount(response.getFinalPaymentAmount().toString())
                .build();
    }

    @ParameterizedTest
    @MethodSource("testPrevValueCounters")
    @DisplayName("Check for exceptions when the current counter value is lower than the previous one.")
    void testValidatePrevValueCounterThrow(Integer previousValue, Integer currentValue) {
        var request = (PaymentHistoryRequest) firstRequest;
        request.setPrevValueCounter(previousValue);
        request.setCurrentValueCounter(currentValue);
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request));
    }

    private static @NonNull Stream<Arguments> testPrevValueCounters() {
        return Stream.of(
                Arguments.of(12, 10),
                Arguments.of(10, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("testFinalPayments")
    @DisplayName("Check exceptions when the entered payment value is not correct.")
    void testValidateFinalPaymentAmountThrow(String finalPay) {
        var request = (PaymentHistoryRequest) firstRequest;
        request.setFinalPaymentAmount(finalPay);
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request));
    }

    private static @NonNull Stream<Arguments> testFinalPayments() {
        return Stream.of(
                Arguments.of("13,5"),
                Arguments.of("ff"),
                Arguments.of("13#5")
        );
    }
}
