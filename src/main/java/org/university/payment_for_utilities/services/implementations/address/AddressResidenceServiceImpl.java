package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.AddressResidenceRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

import java.util.Optional;

@Service
public class AddressResidenceServiceImpl extends CrudServiceAbstract<AddressResidence, AddressResidenceRepository> implements AddressResidenceService {
    private static final String NUM_HOUSE_TEMPLATE = "^[\\dA-ZА-ЯІЇҐ\\-\\s]*$";
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
                .settlement(address.settlement())
                .uaNameStreet(address.uaNameStreet())
                .enNameStreet(address.enNameStreet())
                .numHouse(address.numHouse())
                .numEntrance(address.numEntrance())
                .numApartment(address.numApartment())
                .currentData(true)
                .build();
    }

    @Override
    protected AddressResidence createEntity(Response response) {
        var addressResponse = (AddressResidenceResponse) response;
        return AddressResidence
                .builder()
                .id(addressResponse.id())
                .settlement(addressResponse.settlement())
                .uaNameStreet(addressResponse.uaNameStreet())
                .enNameStreet(addressResponse.enNameStreet())
                .numHouse(addressResponse.numHouse())
                .numEntrance(addressResponse.numEntrance())
                .numApartment(addressResponse.numApartment())
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
    protected void updateEntity(@NonNull AddressResidence entity, @NonNull Request request) {
        var newValue = (AddressResidenceRequest) request;

        if(!newValue.settlement().isEmpty()){
            entity.setSettlement(newValue.settlement());
        }
        if(!newValue.uaNameStreet().isBlank()){
            entity.setUaNameStreet(newValue.uaNameStreet());
        }
        if(!newValue.enNameStreet().isBlank()){
            entity.setEnNameStreet(newValue.enNameStreet());
        }
        if(!newValue.numHouse().isBlank()){
            entity.setNumHouse(newValue.numHouse());
        }
        if(!newValue.numEntrance().isBlank()){
            entity.setNumEntrance(newValue.numEntrance());
        }
        if(!newValue.numApartment().isBlank()){
            entity.setNumApartment(newValue.numApartment());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var addressRequest = (AddressResidenceRequest) request;

        validateStreet(addressRequest.uaNameStreet());
        validateStreet(addressRequest.enNameStreet());
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
        if(isValidNumHouse(request.numHouse())){
            return;
        }

        var message = String.format("You entered the wrong house number format: %s. The house number can contain Cyrillic or Latin letters, numbers, hyphens, and spaces.", request.numHouse());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumHouse(@NonNull String numHouse){
        return numHouse
                .toUpperCase()
                .matches(NUM_HOUSE_TEMPLATE);
    }

    private void validateNumEntrance(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumEntrance(request.numEntrance())){
            return;
        }

        var message = String.format("You entered the wrong format for the entrance number: %s. The entrance number can contain no more than three digits.", request.numEntrance());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumEntrance(@NonNull String numEntrance){
        return numEntrance.matches(NUM_ENTRANCE_TEMPLATE);
    }

    private void validateNumApartment(@NonNull AddressResidenceRequest request) throws InvalidInputDataException {
        if(isValidNumApartment(request.numApartment())){
            return;
        }

        var message = String.format("You entered the wrong apartment number format: %s. The apartment number can contain no more than five digits.", request.numApartment());
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidNumApartment(@NonNull String numApartment){
        return numApartment.matches(NUM_APARTMENT_TEMPLATE);
    }

    @Override
    protected Optional<AddressResidence> findEntity(@NonNull Request request) {
        var address = (AddressResidenceRequest) request;
        return repository
                .findBySettlementAndEnNameStreetAndNumHouseAndNumEntrance(
                        address.settlement(),
                        address.enNameStreet(),
                        address.numHouse(),
                        address.numEntrance()
                );
    }
}
