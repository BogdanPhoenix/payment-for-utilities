package org.university.payment_for_utilities.services.implementations.receipt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.ReceiptRequest;
import org.university.payment_for_utilities.repositories.bank.BankRepository;
import org.university.payment_for_utilities.repositories.receipt.ReceiptRepository;
import org.university.payment_for_utilities.repositories.user.ContractEntityRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockAccrualAmountService;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockMeterReadingService;
import org.university.payment_for_utilities.services.interfaces.receipt.PaymentHistoryService;
import org.university.payment_for_utilities.services.interfaces.receipt.ReceiptService;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class ReceiptServiceImpl extends CrudServiceAbstract<Receipt, ReceiptRepository> implements ReceiptService {
    private static final int MAX_DIFFERENCE_MONTHS = 1;

    private final PaymentHistoryService paymentHistoryService;
    private final BlockAccrualAmountService blockAccrualAmountService;
    private final BlockMeterReadingService blockMeterReadingService;
    private final ContractEntityRepository contractEntityRepository;
    private final BankRepository bankRepository;

    @Autowired
    public ReceiptServiceImpl(
            ReceiptRepository repository,
            PaymentHistoryService paymentHistoryService,
            BlockAccrualAmountService blockAccrualAmountService,
            BlockMeterReadingService blockMeterReadingService,
            ContractEntityRepository contractEntityRepository,
            BankRepository bankRepository
    ) {
        super(repository, "Receipts");

        this.paymentHistoryService = paymentHistoryService;
        this.blockAccrualAmountService = blockAccrualAmountService;
        this.blockMeterReadingService = blockMeterReadingService;
        this.contractEntityRepository = contractEntityRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    protected Receipt createEntity(Request request) {
        var receiptRequest = (ReceiptRequest) request;
        var contractEntity = getContractEntity(receiptRequest.getContractEntity().getId());
        var bank = getBank(receiptRequest.getBank().getId());

        return Receipt
                .builder()
                .contractEntity(contractEntity)
                .bank(bank)
                .billMonth(receiptRequest.getBillMonth())
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull Receipt entity) {
        deactivateChildrenCollection(entity.getPaymentHistories(), paymentHistoryService);
        deactivateChildrenCollection(entity.getBlockAccrualAmounts(), blockAccrualAmountService);
        deactivateChildrenCollection(entity.getBlockMeterReadings(), blockMeterReadingService);
    }

    @Override
    protected void updateEntity(@NonNull Receipt entity, @NonNull Request request) {
        var newValue = (ReceiptRequest) request;

        if(!newValue.getContractEntity().isEmpty()){
            var contractEntity = getContractEntity(newValue.getContractEntity().getId());
            entity.setContractEntity(contractEntity);
        }
        if(!newValue.getBank().isEmpty()){
            var bank = getBank(newValue.getBank().getId());
            entity.setBank(bank);
        }
        if(!newValue.getBillMonth().equals(LocalDate.MIN)){
            entity.setBillMonth(newValue.getBillMonth());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var receiptRequest = (ReceiptRequest) request;
        validateBillMonth(receiptRequest.getBillMonth());
    }

    private void validateBillMonth(@NonNull LocalDate billMonth) throws InvalidInputDataException {
        if(isBillMonth(billMonth)) {
            return;
        }

        var message = String.format("The month and year you entered: \"%s\" has not been validated. The difference between the specified month and the current month should not be more than %s month(s).", billMonth, MAX_DIFFERENCE_MONTHS);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isBillMonth(@NonNull LocalDate billMonth) {
        var period = Period.between(billMonth, LocalDate.now());
        var monthDifference = period.getYears() * 12 + period.getMonths();
        return Math.abs(monthDifference) <= MAX_DIFFERENCE_MONTHS;
    }

    @Override
    protected Optional<Receipt> findEntity(@NonNull Request request) {
        var receiptRequest = (ReceiptRequest) request;
        var contractEntity = getContractEntity(receiptRequest.getContractEntity().getId());
        var bank = getBank(receiptRequest.getBank().getId());

        return repository
                .findByContractEntityAndBankAndBillMonth(
                        contractEntity,
                        bank,
                        receiptRequest.getBillMonth()
                );
    }

    private @NonNull ContractEntity getContractEntity(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(contractEntityRepository, id);
    }

    private @NonNull Bank getBank(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(bankRepository, id);
    }
}
