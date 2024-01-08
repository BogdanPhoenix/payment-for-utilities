package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.address.AddressResidenceRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

import java.util.Optional;

@Slf4j
@Service
public class AddressResidenceServiceImpl extends CrudServiceAbstract<AddressResidence, AddressResidenceRepository> implements AddressResidenceService {
    private static final String NUM_HOUSE_TEMPLATE = "^[\\dA-ZА-ЯІЇҐ\\-\\s]+$";
    private static final String NUM_ENTRANCE_TEMPLATE = "^\\d{0,3}$";
    private static final String NUM_APARTMENT_TEMPLATE = "^\\d{0,5}$";

    protected AddressResidenceServiceImpl(AddressResidenceRepository repository) {
        super(repository, "Addresses residence");
    }

    @Override
    protected AddressResidence createEntity(Request request) {
        var address = (AddressResidenceRequest) request;
        return AddressResidence
                .builder()
                .settlement(address.getSettlement())
                .uaNameStreet(address.getUaNameStreet())
                .enNameStreet(address.getEnNameStreet())
                .numHouse(address.getNumHouse())
                .numEntrance(address.getNumEntrance())
                .numApartment(address.getNumApartment())
                .currentData(true)
                .build();
    }

    @Override
    protected AddressResidence createEntity(Response response) {
        var addressResponse = (AddressResidenceResponse) response;
        return AddressResidence
                .builder()
                .id(addressResponse.getId())
                .settlement(addressResponse.getSettlement())
                .uaNameStreet(addressResponse.getUaNameStreet())
                .enNameStreet(addressResponse.getEnNameStreet())
                .numHouse(addressResponse.getNumHouse())
                .numEntrance(addressResponse.getNumEntrance())
                .numApartment(addressResponse.getNumApartment())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull AddressResidence entity) {
        return AddressResidenceResponse
                .builder()
                .id(entity.getId())
                .settlement(entity.getSettlement())
                .uaNameStreet(entity.getUaNameStreet())
                .enNameStreet(entity.getEnNameStreet())
                .numHouse(entity.getNumHouse())
                .numEntrance(entity.getNumEntrance())
                .numApartment(entity.getNumApartment())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull AddressResidence entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (AddressResidenceRequest) updateRequest.getOldValue();
        var newValue = (AddressResidenceRequest) updateRequest.getNewValue();

        entity.setSettlement(
                updateAttribute(
                        oldValue.getSettlement(),
                        newValue.getSettlement()
                )
        );
        entity.setUaNameStreet(
                updateAttribute(
                        oldValue.getUaNameStreet(),
                        newValue.getUaNameStreet()
                )
        );
        entity.setEnNameStreet(
                updateAttribute(
                        oldValue.getEnNameStreet(),
                        newValue.getEnNameStreet()
                )
        );
        entity.setNumHouse(
                updateAttribute(
                        oldValue.getNumHouse(),
                        newValue.getNumHouse()
                )
        );
        entity.setNumEntrance(
                updateAttribute(
                        oldValue.getNumEntrance(),
                        newValue.getNumEntrance()
                )
        );
        entity.setNumApartment(
                updateAttribute(
                        oldValue.getNumApartment(),
                        newValue.getNumApartment()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var addressRequest = (AddressResidenceRequest) request;

        validateStreet(addressRequest.getUaNameStreet());
        validateStreet(addressRequest.getEnNameStreet());
        validateNumHouse(addressRequest);
        validateNumEntrance(addressRequest);
        validateNumApartment(addressRequest);
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (AddressResidenceRequest) updateRequest.getOldValue();
        var newValue = (AddressResidenceRequest) updateRequest.getNewValue();

        validateStreet(oldValue.getUaNameStreet());
        validateStreet(oldValue.getEnNameStreet());

        validateStreet(newValue.getUaNameStreet());
        validateStreet(newValue.getEnNameStreet());

        validateNumHouse(oldValue);
        validateNumHouse(newValue);

        validateNumEntrance(oldValue);
        validateNumEntrance(newValue);

        validateNumApartment(oldValue);
        validateNumApartment(newValue);
    }

    private void validateStreet(@NonNull String name) throws InvalidInputDataException {
        if(isValidString(name)){
            return;
        }

        var message = String.format("You entered the wrong street name format: %s. The name should contain only Cyrillic and Latin letters, hyphens, and spaces.", name);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private void validateNumHouse(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumHouse(request.getNumHouse())){
            return;
        }

        var message = String.format("You entered the wrong house number format: %s. The house number can contain Cyrillic or Latin letters, numbers, hyphens, and spaces.", request.getNumHouse());
        log.error(message);
        throw new InvalidInputDataException(message);
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
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isValidNumEntrance(@NonNull String numEntrance){
        return numEntrance.matches(NUM_ENTRANCE_TEMPLATE);
    }

    private void validateNumApartment(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumApartment(request.getNumApartment())){
            return;
        }

        var message = String.format("You entered the wrong apartment number format: %s. The apartment number can contain no more than five digits.", request.getNumApartment());
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isValidNumApartment(@NonNull String numApartment){
        return numApartment.matches(NUM_APARTMENT_TEMPLATE);
    }

    @Override
    protected Optional<AddressResidence> findOldEntity(@NonNull Request request) {
        var address = (AddressResidenceRequest) request;
        return repository
                .findBySettlementAndEnNameStreetAndNumHouseAndNumEntrance(
                        address.getSettlement(),
                        address.getEnNameStreet(),
                        address.getNumHouse(),
                        address.getNumEntrance()
                );
    }
}
