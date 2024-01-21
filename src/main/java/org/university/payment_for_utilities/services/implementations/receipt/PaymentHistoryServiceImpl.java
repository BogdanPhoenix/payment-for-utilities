package org.university.payment_for_utilities.services.implementations.receipt;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.receipt.PaymentHistory;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.PaymentHistoryRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.receipt.PaymentHistoryResponse;
import org.university.payment_for_utilities.repositories.receipt.PaymentHistoryRepository;
import org.university.payment_for_utilities.services.implementations.FinanceServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.receipt.PaymentHistoryService;

import java.util.Optional;

import static org.university.payment_for_utilities.domains.receipt.PaymentHistory.EMPTY_COUNTER;

@Service
public class PaymentHistoryServiceImpl extends FinanceServiceAbstract<PaymentHistory, PaymentHistoryRepository> implements PaymentHistoryService {
    protected PaymentHistoryServiceImpl(PaymentHistoryRepository repository) {
        super(repository, "Payment history");
    }

    @Override
    protected PaymentHistory createEntity(Request request) {
        var historyRequest = (PaymentHistoryRequest) request;
        var payment = convertStringToBigDecimal(historyRequest.getFinalPaymentAmount());
        return PaymentHistory
                .builder()
                .receipt(historyRequest.getReceipt())
                .prevValueCounter(historyRequest.getPrevValueCounter())
                .currentValueCounter(historyRequest.getCurrentValueCounter())
                .finalPaymentAmount(payment)
                .build();
    }

    @Override
    protected PaymentHistory createEntity(Response response) {
        var historyResponse = (PaymentHistoryResponse) response;
        var builder = PaymentHistory.builder();
        initEntityBuilder(builder, response);

        return builder
                .receipt(historyResponse.getReceipt())
                .prevValueCounter(historyResponse.getPrevValueCounter())
                .currentValueCounter(historyResponse.getCurrentValueCounter())
                .finalPaymentAmount(historyResponse.getFinalPaymentAmount())
                .build();
    }

    @Override
    protected Response createResponse(PaymentHistory entity) {
        var builder = PaymentHistoryResponse.builder();
        initResponseBuilder(builder, entity);

        return builder
                .receipt(entity.getReceipt())
                .prevValueCounter(entity.getPrevValueCounter())
                .currentValueCounter(entity.getCurrentValueCounter())
                .finalPaymentAmount(entity.getFinalPaymentAmount())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull PaymentHistory entity, @NonNull Request request) {
        var newValue = (PaymentHistoryRequest) request;

        if(!newValue.getReceipt().isEmpty()){
            entity.setReceipt(newValue.getReceipt());
        }
        if(!newValue.getPrevValueCounter().equals(EMPTY_COUNTER)){
            entity.setPrevValueCounter(newValue.getPrevValueCounter());
        }
        if(!newValue.getCurrentValueCounter().equals(EMPTY_COUNTER)){
            entity.setCurrentValueCounter(newValue.getCurrentValueCounter());
        }
        if(!newValue.getFinalPaymentAmount().isBlank()){
            var payment = convertStringToBigDecimal(newValue.getFinalPaymentAmount());
            entity.setFinalPaymentAmount(payment);
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var historyRequest = (PaymentHistoryRequest) request;

        validatePrevValueCounter(historyRequest);
        validateFinance(historyRequest.getFinalPaymentAmount());
    }

    private void validatePrevValueCounter(PaymentHistoryRequest historyRequest) throws InvalidInputDataException {
        if(isPrevValueCounter(historyRequest)) {
            return;
        }

        var message = "The current meter reading you provide cannot be less than or equal to the previous one.";
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isPrevValueCounter(@NonNull PaymentHistoryRequest historyRequest) {
        return historyRequest.getPrevValueCounter() < historyRequest.getCurrentValueCounter();
    }

    @Override
    protected Optional<PaymentHistory> findEntity(@NonNull Request request) {
        var historyRequest = (PaymentHistoryRequest) request;
        return repository
                .findByReceipt(
                        historyRequest.getReceipt()
                );
    }
}
