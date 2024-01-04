package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.repositories.address.SettlementRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;

import java.util.Optional;

@Slf4j
@Service
public class SettlementServiceImpl extends CrudServiceAbstract<Settlement, SettlementRepository> implements SettlementService {
    @Autowired
    public SettlementServiceImpl(SettlementRepository repository) {
        super(repository, "Settlements");
    }


    @Override
    protected Settlement createEntity(Request request) {
        var settlementRequest = (SettlementRequest) request;
        return Settlement
                .builder()
                .type(settlementRequest.getType())
                .zipCode(settlementRequest.getZipCode())
                .name(settlementRequest.getName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Settlement entity) {
        return SettlementResponse
                .builder()
                .id(entity.getId())
                .type(entity.getType())
                .zipCode(entity.getZipCode())
                .name(entity.getName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Settlement entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (SettlementRequest) updateRequest.getOldValue();
        var newValue = (SettlementRequest) updateRequest.getNewValue();

        entity.setType(
                updateAttribute(
                        oldValue.getType(),
                        newValue.getType()
                )
        );
        entity.setZipCode(
                updateAttribute(
                        oldValue.getZipCode(),
                        newValue.getZipCode()
                )
        );
        entity.setName(
                updateAttribute(
                        oldValue.getName(),
                        newValue.getName()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var settlementRequest = (SettlementRequest) request;

        validateRequestEmpty(settlementRequest, fatalMessageAddEntity);
        validateZipCode(settlementRequest.getZipCode());
        validateDuplicate(settlementRequest);
    }

    private void validateZipCode(@NonNull String zipCode) throws InvalidInputDataException{
        if(isValidIndex(zipCode)){
            return;
        }

        var message = String.format("You entered an incorrect zip code: \"%s\". The area code must consist of only numbers and be five characters long.", zipCode);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isValidIndex(@NonNull String zipCode) {
        return zipCode
                .matches("^\\d{5}$");
    }

    @Override
    protected Settlement validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var oldValue = (SettlementRequest) updateRequest.getOldValue();
        var newValue = (SettlementRequest) updateRequest.getNewValue();

        validateRequestEmpty(oldValue, String.format(fatalMessageUpdateEntity, "oldValue"));
        validateRequestEmpty(newValue, String.format(fatalMessageUpdateEntity, "newValue"));
        validateZipCode(oldValue.getZipCode());
        validateZipCode(newValue.getZipCode());
        validateDuplicate(newValue);

        return findOldEntity(updateRequest)
                .orElseThrow(() -> new NotFindEntityInDataBaseException(fatalMessageFindOldEntity));
    }

    @Override
    protected Optional<Settlement> findOldEntity(@NonNull UpdateRequest updateRequest) {
        var settlementRequest = (SettlementRequest) updateRequest.getOldValue();
        return repository
                .findByZipCode(
                        settlementRequest.getZipCode()
                );
    }

    @Override
    protected boolean isDuplicate(@NonNull Request request) {
        var settlementRequest = (SettlementRequest) request;
        return repository
                .findByZipCode(settlementRequest.getZipCode())
                .isPresent();
    }
}
