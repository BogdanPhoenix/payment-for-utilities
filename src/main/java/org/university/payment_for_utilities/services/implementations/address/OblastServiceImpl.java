package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.address.Oblast;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.address.OblastRepository;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.domains.pojo.responses.address.OblastResponse;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastUpdateRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class OblastServiceImpl implements OblastService {
    private static final String MESSAGE_SUCCESS_VALIDATION = "Input data has been successfully validated";
    private final OblastRepository repository;

    @Transactional
    @Override
    public OblastResponse addValue(@NonNull OblastRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        validateAdd(request);

        var newOblast = createEntity(request);
        var result = repository.save(newOblast);

        return createResponse(result);
    }

    @Transactional
    @Override
    public OblastResponse updateValue(@NonNull OblastUpdateRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException {
        var entity = validateUpdate(request);

        entity.setUaName(request.getNewUaValue());
        entity.setEnName(request.getNewEnValue());

        var result = repository.save(entity);

        return createResponse(result);
    }

    @Transactional
    @Override
    public OblastResponse removeValue(Long id) throws NotFindEntityInDataBaseException {
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

    private void validateAdd(@NonNull OblastRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException{
        String messageValidateRequest = "You sent an empty request when trying to add a new record to the \"Oblasts\" table.";
        String messageValidateName = "The name of the area should contain only letters, hyphens, and spaces.";
        String messageValidateDuplicate = String.format("%s region, already present in the \"Oblasts\" table.", request.getEnName());

        log.info("Input to the addValue() method");

        validateRequestEmpty(request, messageValidateRequest);
        validateName(request, messageValidateName);
        validateDuplicate(request, messageValidateDuplicate);

        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    private Oblast validateUpdate(@NonNull OblastUpdateRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        String messageValidateRequest = "You sent an empty query in the \"%s\" field when trying to update a record in the \"Oblasts\" table.";
        String messageValidateName = "The %s area name should contain only letters, hyphens, and spaces.";
        String messageValidateDuplicate = String.format("You cannot change the \"%s\" region entity, since the \"%s\" region is already present in the \"Oblasts\" table.",
                request.getOldEnValue(), request.getNewEnValue());

        log.info("Input to the updateValue() method");

        OblastRequest oldOblast = OblastRequest
                .builder()
                .uaName(request.getOldUaValue())
                .enName(request.getOldEnValue())
                .build();

        OblastRequest newOblast = OblastRequest
                .builder()
                .uaName(request.getNewUaValue())
                .enName(request.getNewEnValue())
                .build();

        validateRequestEmpty(oldOblast, String.format(messageValidateRequest, "oldValue"));
        validateRequestEmpty(newOblast, String.format(messageValidateRequest, "newValue"));
        validateName(oldOblast, String.format(messageValidateName, "current"));
        validateName(newOblast, String.format(messageValidateName, "new"));
        validateDuplicate(newOblast, messageValidateDuplicate);

        var entity = repository
                .findByEnName(request.getOldEnValue())
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Failed to find an entity in the \"Oblasts\" table by the old value when updating the table entity."));

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return entity;
    }

    private void validateRequestEmpty(@NonNull OblastRequest request, String message) throws EmptyRequestException {
        if(!isRequestEmpty(request)){
            return;
        }

        log.error(message);
        throw new EmptyRequestException(message);
    }

    private boolean isRequestEmpty(@NonNull OblastRequest request){
        var uaName = request.getUaName();
        var enName = request.getEnName();

        return uaName == null || uaName.isEmpty() ||
                enName == null || enName.isEmpty();
    }

    private void validateName(@NonNull OblastRequest entity, String message) throws InvalidInputDataException {
        if(isValidName(entity.getUaName()) && isValidName(entity.getEnName())){
            return;
        }

        log.error(message);
        throw new InvalidInputDataException(message);
    }

    @Contract(pure = true)
    private boolean isValidName(@NonNull String name) {
        return name
                .toUpperCase()
                .matches("^[А-ЯІЇҐA-Z\\-\\s]+$");
    }

    private void validateDuplicate(@NonNull OblastRequest request, String message) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        log.error(message);
        throw new DuplicateException(message);
    }

    private boolean isDuplicate(@NonNull OblastRequest request) {
        var entity = repository.findByEnName(request.getEnName());
        return entity.isPresent();
    }

    private Oblast validateRemove(Long id) throws NotFindEntityInDataBaseException {
        log.info("Input to the removeValue() method");

        var oblast = repository
                .findById(id)
                .orElseThrow(() ->
                        new NotFindEntityInDataBaseException(
                                String.format("Unable to find an entity in the \"Oblasts\" table using the specified identifier: %d.", id)
                        )
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return oblast;
    }

    private Oblast createEntity(@NonNull OblastRequest request){
        return Oblast
                .builder()
                .uaName(request.getUaName())
                .enName(request.getEnName())
                .currentData(true)
                .build();
    }

    private OblastResponse createResponse(@NonNull Oblast oblast){
        return OblastResponse
                .builder()
                .id(oblast.getId())
                .uaName(oblast.getUaName())
                .enName(oblast.getEnName())
                .build();
    }
}
