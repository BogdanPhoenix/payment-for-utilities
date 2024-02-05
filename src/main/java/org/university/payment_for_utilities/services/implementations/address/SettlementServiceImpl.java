package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.address.SettlementNameRepository;
import org.university.payment_for_utilities.repositories.address.SettlementRepository;
import org.university.payment_for_utilities.repositories.address.TypeSettlementRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class SettlementServiceImpl extends CrudServiceAbstract<Settlement, SettlementRepository> implements SettlementService {
    private static final String INDEX_TEMPLATE = "^\\d{5}$";
    private final AddressResidenceService addressResidenceService;

    private final TypeSettlementRepository typeSettlementRepository;
    private final SettlementNameRepository settlementNameRepository;

    @Autowired
    public SettlementServiceImpl(
            SettlementRepository repository,
            AddressResidenceService addressResidenceService,
            TypeSettlementRepository typeSettlementRepository,
            SettlementNameRepository settlementNameRepository
    ) {
        super(repository, "Settlements");
        this.addressResidenceService = addressResidenceService;
        this.typeSettlementRepository = typeSettlementRepository;
        this.settlementNameRepository = settlementNameRepository;
    }


    @Override
    protected Settlement createEntity(Request request) {
        var settlementRequest = (SettlementRequest) request;
        var type = getTypeSettlement(settlementRequest.getType());
        var name = getSettlementName(settlementRequest.getName());

        return Settlement
                .builder()
                .type(type)
                .zipCode(settlementRequest.getZipCode())
                .name(name)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull Settlement entity) {
        deactivateChildrenCollection(entity.getAddresses(), addressResidenceService);
    }

    @Override
    protected void updateEntity(@NonNull Settlement entity, @NonNull Request request) {
        var newValue = (SettlementRequest) request;

        if(!newValue.getType().equals(Response.EMPTY_PARENT_ENTITY)){
            var type = getTypeSettlement(newValue.getType());
            entity.setType(type);
        }
        if(!newValue.getZipCode().isBlank()){
            entity.setZipCode(newValue.getZipCode());
        }
        if(!newValue.getName().equals(Response.EMPTY_PARENT_ENTITY)){
            var name = getSettlementName(newValue.getName());
            entity.setName(name);
        }
    }

    private @NonNull TypeSettlement getTypeSettlement(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(typeSettlementRepository, id);
    }

    private @NonNull SettlementName getSettlementName(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(settlementNameRepository, id);
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var settlementRequest = (SettlementRequest) request;
        validateZipCode(settlementRequest.getZipCode());
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
                        settlementRequest.getZipCode()
                );
    }
}
