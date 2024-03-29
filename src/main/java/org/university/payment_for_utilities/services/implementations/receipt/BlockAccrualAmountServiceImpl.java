package org.university.payment_for_utilities.services.implementations.receipt;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.receipt.BlockAccrualAmount;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.BlockAccrualAmountRequest;
import org.university.payment_for_utilities.repositories.receipt.BlockAccrualAmountRepository;
import org.university.payment_for_utilities.repositories.receipt.ReceiptRepository;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.ReceiptSearcherAbstract;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockAccrualAmountService;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.convertStringToBigDecimal;
import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.validateFinance;

@Service
public class BlockAccrualAmountServiceImpl extends ReceiptSearcherAbstract<BlockAccrualAmount, BlockAccrualAmountRepository> implements BlockAccrualAmountService {
    protected BlockAccrualAmountServiceImpl(
            BlockAccrualAmountRepository repository,
            ReceiptRepository receiptRepository
    ) {
        super(repository, "Blocks accrual amount", receiptRepository);
    }

    @Override
    protected BlockAccrualAmount createEntity(Request request) {
        var blockRequest = (BlockAccrualAmountRequest) request;

        var receipt = getReceipt(blockRequest.getReceipt());
        var debtBeginMonth = convertStringToBigDecimal(blockRequest.getDebtBeginMonth());
        var debtEndMonth = convertStringToBigDecimal(blockRequest.getDebtEndMonth());
        var fine = convertStringToBigDecimal(blockRequest.getFine());
        var lastCreditedPayment = convertStringToBigDecimal(blockRequest.getLastCreditedPayment());
        var amountDue = convertStringToBigDecimal(blockRequest.getAmountDue());

        return BlockAccrualAmount
                .builder()
                .receipt(receipt)
                .debtBeginMonth(debtBeginMonth)
                .debtEndMonth(debtEndMonth)
                .fine(fine)
                .lastCreditedPayment(lastCreditedPayment)
                .amountDue(amountDue)
                .build();
    }

    @Override
    protected void updateEntity(@NonNull BlockAccrualAmount entity, @NonNull Request request) {
        super.updateEntity(entity, request);
        var newValue = (BlockAccrualAmountRequest) request;

        if(!newValue.getDebtBeginMonth().isBlank()){
            var value = convertStringToBigDecimal(newValue.getDebtBeginMonth());
            entity.setDebtBeginMonth(value);
        }
        if(!newValue.getDebtEndMonth().isBlank()){
            var value = convertStringToBigDecimal(newValue.getDebtEndMonth());
            entity.setDebtEndMonth(value);
        }
        if(!newValue.getFine().isBlank()){
            var value = convertStringToBigDecimal(newValue.getFine());
            entity.setFine(value);
        }
        if(!newValue.getLastCreditedPayment().isBlank()){
            var value = convertStringToBigDecimal(newValue.getLastCreditedPayment());
            entity.setLastCreditedPayment(value);
        }
        if(!newValue.getAmountDue().isBlank()){
            var value = convertStringToBigDecimal(newValue.getAmountDue());
            entity.setAmountDue(value);
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var blockRequest = (BlockAccrualAmountRequest) request;

        validateFinance("debt begin month", blockRequest.getDebtBeginMonth());
        validateFinance("debt end month", blockRequest.getDebtEndMonth());
        validateFinance("fine", blockRequest.getFine());
        validateFinance("last credited payment", blockRequest.getLastCreditedPayment());
        validateFinance("amount due", blockRequest.getAmountDue());
    }
}
