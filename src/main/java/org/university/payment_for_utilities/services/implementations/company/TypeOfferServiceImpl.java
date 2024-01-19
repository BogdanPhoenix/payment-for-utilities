package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.TypeOffer;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.company.TypeOfferRepository;
import org.university.payment_for_utilities.services.implementations.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.company.TypeOfferService;

import java.util.Optional;

@Service
public class TypeOfferServiceImpl extends TransliterationService<TypeOffer, TypeOfferRepository> implements TypeOfferService {
    protected TypeOfferServiceImpl(TypeOfferRepository repository) {
        super(repository, "Type offers");
    }

    @Override
    protected TypeOffer createEntity(Request request) {
        var typeOfferRequest = (TypeOfferRequest) request;
        return TypeOffer
                .builder()
                .uaName(typeOfferRequest.getUaName())
                .enName(typeOfferRequest.getEnName())
                .unitMeasurement(typeOfferRequest.getUnitMeasurement())
                .build();
    }

    @Override
    protected TypeOffer createEntity(Response response) {
        var typeOfferResponse = (TypeOfferResponse) response;
        return TypeOffer
                .builder()
                .id(typeOfferResponse.getId())
                .uaName(typeOfferResponse.getUaName())
                .enName(typeOfferResponse.getEnName())
                .unitMeasurement(typeOfferResponse.getUnitMeasurement())
                .build();
    }

    @Override
    protected Response createResponse(@NonNull TypeOffer entity) {
        var response = (TypeOfferResponse) initResponseBuilder(TypeOfferResponse.builder(), entity);
        response.setUnitMeasurement(entity.getUnitMeasurement());
        return response;
    }

    @Override
    protected Optional<TypeOffer> findEntity(@NonNull Request request) {
        var typeOfferRequest = (TypeOfferRequest) request;
        return repository
                .findByUnitMeasurementAndEnName(
                        typeOfferRequest.getUnitMeasurement(),
                        typeOfferRequest.getEnName()
                );
    }
}
