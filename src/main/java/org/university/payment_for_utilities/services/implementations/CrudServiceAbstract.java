package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.interfaces.TableInfo;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public abstract class CrudServiceAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> implements CrudService {
    protected static final String MESSAGE_SUCCESS_VALIDATION = "Input data has been successfully validated";
    private static final String NAME_TEMPLATE = "^[A-ZА-ЯІЇҐ\\-.\\s]*$";

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

    protected void validateAdd(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        log.info("Input to the addValue() method");

        validateRequestEmpty(request, fatalMessageAddEntity);
        validationProcedureAddValue(request);
        validateDuplicate(request);

        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    @Transactional
    @Override
    public Response updateValue(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException {
        validateUpdate(updateRequest);

        var entity = findOldEntity(updateRequest.getOldValue())
                .orElseThrow(() -> throwNotFindEntityInDataBaseException(fatalMessageFindOldEntity));

        updateEntity(entity, updateRequest);
        var result = repository.save(entity);

        return createResponse(result);
    }

    protected void validateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        log.info("Input to the updateValue() method");

        validateRequestEmpty(updateRequest.getOldValue(), fatalMessageUpdateEntity);
        validationProcedureValidateUpdate(updateRequest);
        validateDuplicate(updateRequest.getNewValue());

        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    @Transactional
    @Override
    public Response removeValue(@NonNull Request request) throws NotFindEntityInDataBaseException {
        var entity = findOldEntity(request)
                .orElseThrow(() -> {
                        var message = String.format("Could not find an entity in table \"%s\" for the specified query: %s.", tableName, request);
                        return throwNotFindEntityInDataBaseException(message);
                    }
                );

        repository.deleteById(entity.getId());
        return createResponse(entity);
    }

    @Transactional
    @Override
    public Response removeValue(Long id) throws NotFindEntityInDataBaseException {
        var entity = validateFindEntity(id);

        repository.deleteById(id);
        return createResponse(entity);
    }

    protected T validateFindEntity(Long id) throws NotFindEntityInDataBaseException {
        log.info("Input to the removeValue() method");

        var oblast = repository
                .findById(id)
                .orElseThrow(() -> {
                            var message = String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %d.", tableName, id);
                            return throwNotFindEntityInDataBaseException(message);
                        }
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return oblast;
    }

    @Transactional
    @Override
    public Long removeAll() {
        var numDeleteItems = repository.count();
        repository.deleteAll();
        return numDeleteItems;
    }

    protected void validateRequestEmpty(Request request, String message) throws EmptyRequestException {
        if(!isRequestEmpty(request)){
            return;
        }

        throwRuntimeException(message, EmptyRequestException::new);
    }

    private boolean isRequestEmpty(Request request){
        return request == null || request.isEmpty();
    }

    protected void validateDuplicate(@NonNull Request request) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        throwRuntimeException(message, DuplicateException::new);
    }

    protected boolean isDuplicate(@NonNull Request request){
        return findOldEntity(request)
                .isPresent();
    }

    protected void validateName(@NonNull String name) throws InvalidInputDataException{
        if(isValidString(name)){
            return;
        }

        var message = String.format("The data in the %s query should contain only Cyrillic and Latin letters, hyphens, and spaces.", name);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    @Contract(pure = true)
    protected boolean isValidString(@NonNull String name) {
        return name
                .toUpperCase()
                .matches(NAME_TEMPLATE);
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

    protected NotFindEntityInDataBaseException throwNotFindEntityInDataBaseException(@NonNull String message){
        log.error(message);
        return new NotFindEntityInDataBaseException(message);
    }

    protected void throwRuntimeException(@NonNull String message, @NonNull Function<String, ? extends RuntimeException> exception){
        log.error(message);
        throw exception.apply(message);
    }

    protected abstract T createEntity(Request request);
    protected abstract T createEntity(Response response);
    protected abstract Response createResponse(T entity);
    protected abstract void updateEntity(@NonNull T entity, @NonNull UpdateRequest updateRequest);
    protected abstract void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException;
    protected abstract void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException;
    protected abstract Optional<T> findOldEntity(@NonNull Request request);
}
