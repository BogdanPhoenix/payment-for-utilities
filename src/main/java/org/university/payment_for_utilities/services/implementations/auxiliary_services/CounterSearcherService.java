package org.university.payment_for_utilities.services.implementations.auxiliary_services;

import lombok.NonNull;
import org.university.payment_for_utilities.domains.abstract_class.CounterSearcher;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.CounterSearcherRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.CounterSearcherRepository;
import org.university.payment_for_utilities.repositories.receipt.ReceiptRepository;

import static org.university.payment_for_utilities.domains.abstract_class.CounterSearcher.EMPTY_COUNTER;
import static org.university.payment_for_utilities.services.implementations.tools.CounterTools.validatePrevValueCounter;

public abstract class CounterSearcherService<T extends CounterSearcher, J extends CounterSearcherRepository<T>> extends ReceiptSearcherAbstract<T, J> {
    protected CounterSearcherService(
            J repository,
            String tableName,
            ReceiptRepository receiptRepository
    ) {
        super(repository, tableName, receiptRepository);
    }

    @Override
    protected void updateEntity(@NonNull T entity, @NonNull Request request) {
        super.updateEntity(entity, request);
        var newValue = (CounterSearcherRequest) request;

        if(!newValue.getPrevValueCounter().equals(EMPTY_COUNTER)){
            entity.setPrevValueCounter(newValue.getPrevValueCounter());
        }
        if(!newValue.getCurrentValueCounter().equals(EMPTY_COUNTER)){
            entity.setCurrentValueCounter(newValue.getCurrentValueCounter());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var counterRequest = (CounterSearcherRequest) request;
        validatePrevValueCounter(counterRequest);
    }
}
