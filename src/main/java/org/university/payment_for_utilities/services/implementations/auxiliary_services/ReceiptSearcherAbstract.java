package org.university.payment_for_utilities.services.implementations.auxiliary_services;

import lombok.NonNull;
import org.university.payment_for_utilities.domains.abstract_class.ReceiptSearcher;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.pojo.requests.abstract_class.ReceiptSearcherRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.ReceiptSearcherRepository;
import org.university.payment_for_utilities.repositories.receipt.ReceiptRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;

import java.util.Optional;

public abstract class ReceiptSearcherAbstract<T extends ReceiptSearcher, J extends ReceiptSearcherRepository<T>> extends CrudServiceAbstract<T, J> {
    private final ReceiptRepository receiptRepository;
    protected ReceiptSearcherAbstract(
            J repository,
            String tableName,
            ReceiptRepository receiptRepository
    ) {
        super(repository, tableName);
        this.receiptRepository = receiptRepository;
    }

    @Override
    protected void updateEntity(@NonNull T entity, @NonNull Request request) {
        var newValue = (ReceiptSearcherRequest) request;

        if(!newValue.getReceipt().equals(Response.EMPTY_PARENT_ENTITY)) {
            var receipt = getReceipt(newValue.getReceipt());
            entity.setReceipt(receipt);
        }
    }

    @Override
    protected Optional<T> findEntity(@NonNull Request request) {
        var receiptSearcher = (ReceiptSearcherRequest) request;
        var receipt = getReceipt(receiptSearcher.getReceipt());

        return repository.findByReceipt(receipt);
    }

    protected @NonNull Receipt getReceipt(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(receiptRepository, id);
    }
}
