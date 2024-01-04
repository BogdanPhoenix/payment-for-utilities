package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;

import java.util.Optional;

@Slf4j
public abstract class TransliterationService<T, J extends TableSearcherRepository<T>> extends CrudServiceAbstract<T, J> {
    protected TransliterationService(J repository, String tableName) {
        super(repository, tableName);
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var transliterationRequest = (TransliterationRequest) request;

        validateRequestEmpty(transliterationRequest, fatalMessageAddEntity);
        validateName(transliterationRequest.getUaName());
        validateName(transliterationRequest.getEnName());
        validateDuplicate(transliterationRequest);
    }

    @Override
    protected T validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var oldValue = (TransliterationRequest) updateRequest.getOldValue();
        var newValue = (TransliterationRequest) updateRequest.getNewValue();

        validateRequestEmpty(oldValue, String.format(fatalMessageUpdateEntity, "oldValue"));
        validateRequestEmpty(newValue, String.format(fatalMessageUpdateEntity, "newValue"));
        validateName(oldValue.getUaName());
        validateName(oldValue.getEnName());
        validateName(newValue.getUaName());
        validateName(newValue.getEnName());
        validateDuplicate(newValue);

        return findOldEntity(updateRequest)
                .orElseThrow(() -> new NotFindEntityInDataBaseException(fatalMessageFindOldEntity));
    }

    @Override
    protected Optional<T> findOldEntity(@NonNull UpdateRequest updateRequest) {
        var transliterationRequest = (TransliterationRequest) updateRequest.getOldValue();

        return repository
                .findByEnName(
                        transliterationRequest.getEnName()
                );
    }

    @Override
    protected boolean isDuplicate(@NonNull Request request) {
        var transliterationRequest = (TransliterationRequest) request;
        return repository
                .findByEnName(
                        transliterationRequest.getEnName()
                )
                .isPresent();
    }

    private void validateName(@NonNull String name) throws InvalidInputDataException{
        if(isValidName(name)){
            return;
        }

        var message = String.format("The data in the %s query should contain only Cyrillic and Latin letters, hyphens, and spaces.", name);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    @Contract(pure = true)
    private boolean isValidName(@NonNull String name) {
        return name
                .toUpperCase()
                .matches("^[А-ЯІЇҐA-Z\\-\\s]*$");
    }
}
