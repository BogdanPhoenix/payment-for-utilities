package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
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
                .edrpou(edrpouRequest.getEdrpou())
                .build();
    }

    @Override
    protected Edrpou createEntity(Response response) {
        var edrpouResponse = (EdrpouResponse) response;
        var builder = Edrpou.builder();
        initEntityBuilder(builder, response);

        return builder
                .edrpou(edrpouResponse.getEdrpou())
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Edrpou entity) {
        var builder = EdrpouResponse.builder();
        initResponseBuilder(builder, entity);

        return builder
                .edrpou(entity.getEdrpou())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Edrpou entity, @NonNull Request request) {
        var newValue = (EdrpouRequest) request;

        if(!newValue.getEdrpou().isBlank()){
            entity.setEdrpou(newValue.getEdrpou());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var edrpouRequest = (EdrpouRequest) request;
        validateEdrpou(edrpouRequest.getEdrpou());
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
    protected Optional<Edrpou> findEntity(@NonNull Request request) {
        var edrpouRequest = (EdrpouRequest) request;
        return repository
                .findByEdrpou(
                        edrpouRequest.getEdrpou()
                );
    }
}
