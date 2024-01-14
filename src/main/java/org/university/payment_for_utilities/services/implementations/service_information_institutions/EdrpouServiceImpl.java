package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.repositories.service_information_institutions.EdrpouRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;

import java.util.Optional;

@Service
public class EdrpouServiceImpl extends CrudServiceAbstract<Edrpou, EdrpouRepository> implements EdrpouService {
    private static final String EDRPOU_TEMPLATE = "^\\d{8}$";

    protected EdrpouServiceImpl(EdrpouRepository repository) {
        super(repository, "Edrpou codes");
    }

    @Override
    protected Edrpou createEntity(Request request) {
        var edrpouRequest = (EdrpouRequest) request;
        return Edrpou
                .builder()
                .edrpou(edrpouRequest.edrpou())
                .currentData(true)
                .build();
    }

    @Override
    protected Edrpou createEntity(Response response) {
        var edrpouResponse = (EdrpouResponse) response;
        return Edrpou
                .builder()
                .id(edrpouResponse.id())
                .edrpou(edrpouResponse.edrpou())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Edrpou entity) {
        return EdrpouResponse
                .builder()
                .id(entity.getId())
                .edrpou(entity.getEdrpou())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Edrpou entity, @NonNull Request request) {
        var newValue = (EdrpouRequest) request;

        if(!newValue.edrpou().isBlank()){
            entity.setEdrpou(newValue.edrpou());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var edrpouRequest = (EdrpouRequest) request;
        validateEdrpou(edrpouRequest.edrpou());
    }

    private void validateEdrpou(@NonNull String edrpou) throws InvalidInputDataException {
        if (isEdrpou(edrpou)){
            return;
        }

        var message = String.format("The EDRPOU: \"%s\" of the bank has not been validated. It should contain only eight digits.", edrpou);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isEdrpou(@NonNull String edrpou){
        return edrpou.isBlank() || edrpou
                .matches(EDRPOU_TEMPLATE);
    }

    @Override
    protected Optional<Edrpou> findOldEntity(@NonNull Request request) {
        var edrpouRequest = (EdrpouRequest) request;
        return repository
                .findByEdrpou(
                        edrpouRequest.edrpou()
                );
    }
}
