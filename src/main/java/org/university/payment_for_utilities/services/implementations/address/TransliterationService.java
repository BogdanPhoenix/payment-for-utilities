package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;

import java.util.Optional;

@Slf4j
public abstract class TransliterationService<T extends TableInfo, J extends TableSearcherRepository<T>> extends CrudServiceAbstract<T, J> {
    protected TransliterationService(J repository, String tableName) {
        super(repository, tableName);
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var transliterationRequest = (TransliterationRequest) request;

        validateName(transliterationRequest.getUaName());
        validateName(transliterationRequest.getEnName());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (TransliterationRequest) updateRequest.getOldValue();
        var newValue = (TransliterationRequest) updateRequest.getNewValue();

        validateName(oldValue.getUaName());
        validateName(oldValue.getEnName());
        validateName(newValue.getUaName());
        validateName(newValue.getEnName());
    }

    @Override
    protected Optional<T> findOldEntity(@NonNull Request request) {
        var transliterationRequest = (TransliterationRequest) request;
        return repository
                .findByEnName(
                        transliterationRequest.getEnName()
                );
    }
}
