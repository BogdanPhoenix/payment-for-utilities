package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.address.SettlementRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;

import java.util.Optional;

@Service
public class SettlementServiceImpl extends CrudServiceAbstract<Settlement, SettlementRepository> implements SettlementService {
    private static final String INDEX_TEMPLATE = "^\\d{5}$";

    @Autowired
    public SettlementServiceImpl(SettlementRepository repository) {
        super(repository, "Settlements");
    }


    @Override
    protected Settlement createEntity(Request request) {
        var settlementRequest = (SettlementRequest) request;
        return Settlement
                .builder()
                .type(settlementRequest.type())
                .zipCode(settlementRequest.zipCode())
                .name(settlementRequest.name())
                .currentData(true)
                .build();
    }

    @Override
    protected Settlement createEntity(Response response) {
        var settlementResponse = (SettlementResponse) response;
        return Settlement
                .builder()
                .id(settlementResponse.id())
                .type(settlementResponse.type())
                .zipCode(settlementResponse.zipCode())
                .name(settlementResponse.name())
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
    protected void updateEntity(@NonNull Settlement entity, @NonNull Request request) {
        var newValue = (SettlementRequest) request;

        if(!newValue.type().isEmpty()){
            entity.setType(newValue.type());
        }
        if(!newValue.zipCode().isBlank()){
            entity.setZipCode(newValue.zipCode());
        }
        if(!newValue.name().isEmpty()){
            entity.setName(newValue.name());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var settlementRequest = (SettlementRequest) request;
        validateZipCode(settlementRequest.zipCode());
    }

    private void validateZipCode(@NonNull String zipCode) throws InvalidInputDataException{
        if(isValidIndex(zipCode)){
            return;
        }

        var message = String.format("You entered an incorrect zip code: \"%s\". The area code must consist of only numbers and be five characters long.", zipCode);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidIndex(@NonNull String zipCode) {
        return zipCode.isBlank() || zipCode
                .matches(INDEX_TEMPLATE);
    }

    @Override
    protected Optional<Settlement> findEntity(@NonNull Request request) {
        var settlementRequest = (SettlementRequest) request;
        return repository
                .findByZipCode(
                        settlementRequest.zipCode()
                );
    }
}
