package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.domains.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class CrudServiceAbstract<T, J extends JpaRepository<T, Long>> implements CrudService {
    private static final String MESSAGE_SUCCESS_VALIDATION = "Input data has been successfully validated";

    protected J repository;
    protected String tableName;

    @Transactional(readOnly = true)
    @Override
    public List<Response> getAll(){
        return repository
                .findAll()
                .stream()
                .map(this::createResponse)
                .toList();
    }

    @Transactional
    @Override
    public Response addValue(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        validateAdd(request);

        var newEntity = createEntity(request);
        var result = repository.save(newEntity);

        return createResponse(result);
    }

    @Transactional
    @Override
    public Response updateValue(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException {
        var entity = validateUpdate(updateRequest);
        updateEntity(entity, updateRequest);

        var result = repository.save(entity);

        return createResponse(result);
    }

    @Transactional
    @Override
    public Response removeValue(Long id) throws NotFindEntityInDataBaseException {
        var entity = validateRemove(id);

        repository.deleteById(id);
        return createResponse(entity);
    }

    @Transactional
    @Override
    public Long removeAll() {
        var numDeleteItems = repository.count();
        repository.deleteAll();
        return numDeleteItems;
    }

    protected void validateAdd(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException{
        String messageValidateRequest = String.format("You sent an empty request when trying to add a new record to the \"%s\" table.", tableName);

        log.info("Input to the addValue() method");

        validateRequestEmpty(request, messageValidateRequest);
        validateName(request);
        validateDuplicate(request);

        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    protected T validateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        String messageValidateRequest = "You sent an empty query in the \"%s\" field when trying to update a record in the \"" + tableName + "\" table.";

        log.info("Input to the updateValue() method");

        validateRequestEmpty(updateRequest.getOldValue(), String.format(messageValidateRequest, "oldValue"));
        validateRequestEmpty(updateRequest.getNewValue(), String.format(messageValidateRequest, "newValue"));
        validateName(updateRequest.getOldValue());
        validateName(updateRequest.getNewValue());
        validateDuplicate(updateRequest.getNewValue());

        var entity = findOldEntity(updateRequest)
                .orElseThrow(() -> new NotFindEntityInDataBaseException(
                        String.format("Failed to find an entity in the \"%s\" table by the old value when updating the table entity.", tableName))
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return entity;
    }

    protected T validateRemove(Long id) throws NotFindEntityInDataBaseException {
        log.info("Input to the removeValue() method");

        var oblast = repository
                .findById(id)
                .orElseThrow(() ->
                        new NotFindEntityInDataBaseException(
                                String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %d.", tableName, id)
                        )
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return oblast;
    }

    private void validateRequestEmpty(Request request, String message) throws EmptyRequestException {
        if(!isRequestEmpty(request)){
            return;
        }

        log.error(message);
        throw new EmptyRequestException(message);
    }

    private boolean isRequestEmpty(Request request){
        return request == null ||
                request.getUaName() == null ||
                request.getEnName() == null;
    }

    private void validateName(@NonNull Request request){
        if(isValidName(request.getUaName()) && isValidName(request.getEnName())){
            return;
        }

        var message = String.format("The data in the %s and %s query should contain only Cyrillic and Latin letters, hyphens, and spaces.",
                request.getUaName(), request.getEnName());
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    @Contract(pure = true)
    private boolean isValidName(@NonNull String name) {
        return name
                .toUpperCase()
                .matches("^[А-ЯІЇҐA-Z\\-\\s]*$");
    }

    private void validateDuplicate(@NonNull Request request) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request.getEnName(), tableName);
        log.error(message);
        throw new DuplicateException(message);
    }

    protected String updatePrimitive(String oldValue, @NonNull String newValue){
        return newValue.isEmpty() ? oldValue : newValue;
    }

    protected abstract boolean isDuplicate(@NonNull Request request);
    protected abstract T createEntity(Request request);
    protected abstract Response createResponse(T entity);
    protected abstract void updateEntity(T entity, @NonNull UpdateRequest updateRequest);
    protected abstract Optional<T> findOldEntity(@NonNull UpdateRequest updateRequest);
}
