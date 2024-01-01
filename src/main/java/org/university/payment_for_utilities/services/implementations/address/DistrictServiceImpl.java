package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.address.District;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictUpdateRequest;
import org.university.payment_for_utilities.domains.pojo.responses.address.DistrictResponse;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.repositories.address.DistrictRepository;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private static final String MESSAGE_SUCCESS_VALIDATION = "Input data has been successfully validated";
    private final DistrictRepository repository;

    @Transactional
    @Override
    public DistrictResponse addValue(@NonNull DistrictRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        validateAdd(request);

        var newOblast = createEntity(request);
        var result = repository.save(newOblast);

        return createResponse(result);
    }

    @Transactional
    @Override
    public DistrictResponse updateValue(@NonNull DistrictUpdateRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException {
        var entity = validateUpdate(request);

        entity.setUaName(request.getNewUaValue());
        entity.setEnName(request.getNewEnValue());

        var result = repository.save(entity);

        return createResponse(result);
    }

    @Transactional
    @Override
    public DistrictResponse removeValue(Long id) throws NotFindEntityInDataBaseException {
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

    private void validateAdd(@NonNull DistrictRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException{
        String messageValidateRequest = "You sent an empty request when trying to add a new record to the \"Districts\" table.";
        String messageValidateName = "The name of the area should contain only letters, hyphens, and spaces.";
        String messageValidateDuplicate = String.format("%s region, already present in the \"Districts\" table.", request.getEnName());

        log.info("Input to the addValue() method");

        validateRequestEmpty(request, messageValidateRequest);
        validateName(request, messageValidateName);
        validateDuplicate(request, messageValidateDuplicate);

        log.info(MESSAGE_SUCCESS_VALIDATION);
    }

    private District validateUpdate(@NonNull DistrictUpdateRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        String messageValidateRequest = "You sent an empty query in the \"%s\" field when trying to update a record in the \"Districts\" table.";
        String messageValidateName = "The %s area name should contain only letters, hyphens, and spaces.";
        String messageValidateDuplicate = String.format("You cannot change the \"%s\" region entity, since the \"%s\" region is already present in the \"Districts\" table.",
                request.getOldEnValue(), request.getNewEnValue());

        log.info("Input to the updateValue() method");

        DistrictRequest oldOblast = DistrictRequest
                .builder()
                .uaName(request.getOldUaValue())
                .enName(request.getOldEnValue())
                .build();

        DistrictRequest newOblast = DistrictRequest
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
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Failed to find an entity in the \"Districts\" table by the old value when updating the table entity."));

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return entity;
    }

    private void validateRequestEmpty(@NonNull DistrictRequest request, String message) throws EmptyRequestException {
        if(!isRequestEmpty(request)){
            return;
        }

        log.error(message);
        throw new EmptyRequestException(message);
    }

    private boolean isRequestEmpty(@NonNull DistrictRequest request){
        var uaName = request.getUaName();
        var enName = request.getEnName();

        return uaName == null || uaName.isEmpty() ||
                enName == null || enName.isEmpty();
    }

    private void validateName(@NonNull DistrictRequest entity, String message) throws InvalidInputDataException {
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

    private void validateDuplicate(@NonNull DistrictRequest request, String message) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        log.error(message);
        throw new DuplicateException(message);
    }

    private boolean isDuplicate(@NonNull DistrictRequest request) {
        var entity = repository.findByEnName(request.getEnName());
        return entity.isPresent();
    }

    private District validateRemove(Long id) throws NotFindEntityInDataBaseException {
        log.info("Input to the removeValue() method");

        var oblast = repository
                .findById(id)
                .orElseThrow(() ->
                        new NotFindEntityInDataBaseException(
                                String.format("Unable to find an entity in the \"Districts\" table using the specified identifier: %d.", id)
                        )
                );

        log.info(MESSAGE_SUCCESS_VALIDATION);

        return oblast;
    }

    private District createEntity(@NonNull DistrictRequest request){
        return District
                .builder()
                .uaName(request.getUaName())
                .enName(request.getEnName())
                .currentData(true)
                .build();
    }

    private DistrictResponse createResponse(@NonNull District oblast){
        return DistrictResponse
                .builder()
                .id(oblast.getId())
                .uaName(oblast.getUaName())
                .enName(oblast.getEnName())
                .build();
    }
}
