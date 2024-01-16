package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;

import java.util.Optional;

@Slf4j
public abstract class TransliterationService<T extends TransliterationProperty, J extends TableSearcherRepository<T>> extends CrudServiceAbstract<T, J> {
    protected TransliterationService(J repository, String tableName) {
        super(repository, tableName);
    }

    @Override
    protected void updateEntity(@NonNull T entity, @NonNull Request request) {
        var newValue = (TransliterationRequest) request;

        if(!newValue.uaName().isBlank()){
            entity.setUaName(newValue.uaName());
        }
        if(!newValue.enName().isBlank()){
            entity.setEnName(newValue.enName());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var transliterationRequest = (TransliterationRequest) request;

        validateName(transliterationRequest.uaName());
        validateName(transliterationRequest.enName());
    }

    @Override
    protected Optional<T> findEntity(@NonNull Request request) {
        var transliterationRequest = (TransliterationRequest) request;
        return repository
                .findByEnName(
                        transliterationRequest.enName()
                );
    }
}
