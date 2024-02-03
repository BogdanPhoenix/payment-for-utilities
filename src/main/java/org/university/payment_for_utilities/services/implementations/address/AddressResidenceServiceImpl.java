package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.address.AddressResidenceRepository;
import org.university.payment_for_utilities.repositories.address.SettlementRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;
import org.university.payment_for_utilities.services.interfaces.company.CompanyService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class AddressResidenceServiceImpl extends CrudServiceAbstract<AddressResidence, AddressResidenceRepository> implements AddressResidenceService {
    private static final String NUM_HOUSE_TEMPLATE = "^[\\dA-ZА-ЯІЇҐ\\-\\s]*$";
    private static final String NUM_ENTRANCE_TEMPLATE = "^\\d{0,3}$";
    private static final String NUM_APARTMENT_TEMPLATE = "^\\d{0,5}$";

    private final CompanyService companyService;
    private final SettlementRepository settlementRepository;

    @Autowired
    public AddressResidenceServiceImpl(
            AddressResidenceRepository repository,
            CompanyService companyService,
            SettlementRepository settlementRepository
    ) {
        super(repository, "Addresses residence");
        this.companyService = companyService;
        this.settlementRepository = settlementRepository;
    }

    @Override
    protected AddressResidence createEntity(Request request) {
        var addressRequest = (AddressResidenceRequest) request;
        var settlement = getSettlement(addressRequest.getSettlement().getId());

        return AddressResidence
                .builder()
                .settlement(settlement)
                .uaNameStreet(addressRequest.getUaNameStreet())
                .enNameStreet(addressRequest.getEnNameStreet())
                .numHouse(addressRequest.getNumHouse())
                .numEntrance(addressRequest.getNumEntrance())
                .numApartment(addressRequest.getNumApartment())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull AddressResidence entity, @NonNull Request request) {
        var newValue = (AddressResidenceRequest) request;

        if(!newValue.getSettlement().isEmpty()){
            var settlement = getSettlement(newValue.getSettlement().getId());
            entity.setSettlement(settlement);
        }
        if(!newValue.getUaNameStreet().isBlank()){
            entity.setUaNameStreet(newValue.getUaNameStreet());
        }
        if(!newValue.getEnNameStreet().isBlank()){
            entity.setEnNameStreet(newValue.getEnNameStreet());
        }
        if(!newValue.getNumHouse().isBlank()){
            entity.setNumHouse(newValue.getNumHouse());
        }
        if(!newValue.getNumEntrance().isBlank()){
            entity.setNumEntrance(newValue.getNumEntrance());
        }
        if(!newValue.getNumApartment().isBlank()){
            entity.setNumApartment(newValue.getNumApartment());
        }
    }

    @Override
    protected void deactivatedChildren(@NonNull AddressResidence entity) {
        deactivateChild(entity.getCompany(), companyService);
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var addressRequest = (AddressResidenceRequest) request;

        validateStreet(addressRequest.getUaNameStreet());
        validateStreet(addressRequest.getEnNameStreet());
        validateNumHouse(addressRequest);
        validateNumEntrance(addressRequest);
        validateNumApartment(addressRequest);
    }

    private void validateStreet(@NonNull String name) throws InvalidInputDataException {
        if(isValidString(name)){
            return;
        }

        var message = String.format("You entered the wrong street name format: %s. The name should contain only Cyrillic and Latin letters, hyphens, and spaces.", name);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private void validateNumHouse(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumHouse(request.getNumHouse())){
            return;
        }

        var message = String.format("You entered the wrong house number format: %s. The house number can contain Cyrillic or Latin letters, numbers, hyphens, and spaces.", request.getNumHouse());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumHouse(@NonNull String numHouse){
        return numHouse
                .toUpperCase()
                .matches(NUM_HOUSE_TEMPLATE);
    }

    private void validateNumEntrance(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumEntrance(request.getNumEntrance())){
            return;
        }

        var message = String.format("You entered the wrong format for the entrance number: %s. The entrance number can contain no more than three digits.", request.getNumEntrance());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumEntrance(@NonNull String numEntrance){
        return numEntrance.matches(NUM_ENTRANCE_TEMPLATE);
    }

    private void validateNumApartment(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumApartment(request.getNumApartment())){
            return;
        }

        var message = String.format("You entered the wrong apartment number format: %s. The apartment number can contain no more than five digits.", request.getNumApartment());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumApartment(@NonNull String numApartment){
        return numApartment.matches(NUM_APARTMENT_TEMPLATE);
    }

    @Override
    protected Optional<AddressResidence> findEntity(@NonNull Request request) {
        var addressRequest = (AddressResidenceRequest) request;
        var settlement = getSettlement(addressRequest.getSettlement().getId());

        return repository
                .findBySettlementAndEnNameStreetAndNumHouseAndNumEntrance(
                        settlement,
                        addressRequest.getEnNameStreet(),
                        addressRequest.getNumHouse(),
                        addressRequest.getNumEntrance()
                );
    }

    private @NonNull Settlement getSettlement(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(settlementRepository, id);
    }
}
