package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class CrudServiceAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> implements CrudService {
    protected static final String MESSAGE_SUCCESS_VALIDATION = "Input data has been successfully validated";

    protected final String fatalMessageAddEntity;
    protected final String fatalMessageUpdateEntity;
    protected final String fatalMessageFindOldEntity;

    protected J repository;
    protected String tableName;

    protected CrudServiceAbstract(J repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;

        fatalMessageAddEntity = String.format("You sent an empty request when trying to add a new record to the \"%s\" table.", tableName);
        fatalMessageUpdateEntity = String.format("You sent an empty query in the \"oldValue\" field when trying to update a record in the \"%s\" table.", tableName);
        fatalMessageFindOldEntity = String.format("Failed to find an entity in the \"%s\" table by the old value when updating the table entity.", tableName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Response> getAll(){
        return repository
                .findAll()
                .stream()
                .map(this::createResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Response getById(Long id) throws NotFindEntityInDataBaseException{
        var entity = validateFindEntity(id);
        return createResponse(entity);
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
        var entity = validateFindEntity(id);

        repository.deleteById(id);
        return createResponse(entity);
    }

    @Transactional
    @Override
    public Response removeValue(@NonNull Request request) throws NotFindEntityInDataBaseException {
        var entity = validateFindEntity(request);

        repository.deleteById(entity.getId());
        return createResponse(entity);
    }

    @Transactional
    @Override
    public Long removeAll() {
        var numDeleteItems = repository.count();
        repository.deleteAll();
        return numDeleteItems;
    }

    protected void validateAdd(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        log.info("Input to the addValue() method");
        validationProcedureAddValue(request);
        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    protected T validateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        log.info("Input to the updateValue() method");
        var entity = validationProcedureValidateUpdate(updateRequest);
        log.info(MESSAGE_SUCCESS_VALIDATION);

        return entity;
    }

    protected T validateFindEntity(Long id) throws NotFindEntityInDataBaseException {
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


    protected T validateFindEntity(@NonNull Request request) throws NotFindEntityInDataBaseException {
        log.info("Input to the removeValue() method");

        var entity = findOldEntity(request)
                .orElseThrow(() ->
                        new NotFindEntityInDataBaseException(
                                String.format("Could not find an entity in table \"%s\" for the specified query: %s.", tableName, request)
                        )
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return entity;
    }

    protected void validateRequestEmpty(Request request, String message) throws EmptyRequestException {
        if(!isRequestEmpty(request)){
            return;
        }

        log.error(message);
        throw new EmptyRequestException(message);
    }

    private boolean isRequestEmpty(Request request){
        return request == null || request.isEmpty();
    }

    protected void validateDuplicate(@NonNull Request request) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        log.error(message);
        throw new DuplicateException(message);
    }

    protected boolean isDuplicate(@NonNull Request request){
        return findOldEntity(request)
                .isPresent();
    }

    protected String updateAttribute(@NonNull String oldValue, @NonNull String newValue){
        return newValue.isEmpty()
                ? oldValue
                : newValue;
    }

    protected <R extends TableInfo> R updateAttribute(R oldValue, R newValue){
        return newValue == null || newValue.isEmpty()
                ? oldValue
                : newValue;
    }

    @Contract(pure = true)
    protected boolean isValidString(@NonNull String name) {
        return name
                .toUpperCase()
                .matches("^[A-ZА-ЯІЇҐ\\-\\s]*$");
    }

    protected abstract T createEntity(Request request);
    protected abstract T createEntity(Response response);
    protected abstract Response createResponse(T entity);
    protected abstract void updateEntity(T entity, @NonNull UpdateRequest updateRequest);
    protected abstract void validationProcedureAddValue(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException;
    protected abstract T validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException;
    protected abstract Optional<T> findOldEntity(@NonNull Request request);
}
