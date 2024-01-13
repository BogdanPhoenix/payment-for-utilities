package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.university.payment_for_utilities.domains.interfaces.TransliterationProperty;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;

import java.util.Optional;

@Slf4j
public abstract class TransliterationService<T extends TransliterationProperty, J extends TableSearcherRepository<T>> extends CrudServiceAbstract<T, J> {
    protected TransliterationService(J repository, String tableName) {
        super(repository, tableName);
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var transliterationRequest = (TransliterationRequest) request;

        validateName(transliterationRequest.uaName());
        validateName(transliterationRequest.enName());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (TransliterationRequest) updateRequest.getOldValue();
        var newValue = (TransliterationRequest) updateRequest.getNewValue();

        validateName(oldValue.uaName());
        validateName(oldValue.enName());
        validateName(newValue.uaName());
        validateName(newValue.enName());
    }

    @Override
    protected Optional<T> findOldEntity(@NonNull Request request) {
        var transliterationRequest = (TransliterationRequest) request;
        return repository
                .findByEnName(
                        transliterationRequest.enName()
                );
    }

    @Override
    protected void updateEntity(@NonNull T entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (TransliterationRequest) updateRequest.getOldValue();
        var newValue = (TransliterationRequest) updateRequest.getNewValue();

        entity.setUaName(
                updateAttribute(
                        oldValue.uaName(),
                        newValue.uaName()
                )
        );

        entity.setEnName(
                updateAttribute(
                        oldValue.enName(),
                        newValue.enName()
                )
        );
    }
}
