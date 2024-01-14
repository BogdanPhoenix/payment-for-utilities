package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.TypeOffer;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
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
                .unitMeasurement(typeOfferRequest.unitMeasurement())
                .uaName(typeOfferRequest.uaName())
                .enName(typeOfferRequest.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected TypeOffer createEntity(Response response) {
        var typeOfferResponse = (TypeOfferResponse) response;
        return TypeOffer
                .builder()
                .id(typeOfferResponse.id())
                .unitMeasurement(typeOfferResponse.unitMeasurement())
                .uaName(typeOfferResponse.uaName())
                .enName(typeOfferResponse.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull TypeOffer entity) {
        return TypeOfferResponse
                .builder()
                .id(entity.getId())
                .unitMeasurement(entity.getUnitMeasurement())
                .uaName(entity.getUaName())
                .enName(entity.getEnName())
                .build();
    }

    @Override
    protected Optional<TypeOffer> findOldEntity(@NonNull Request request) {
        var typeOfferRequest = (TypeOfferRequest) request;

        if(typeOfferRequest.unitMeasurement().isEmpty()){
            return Optional.empty();
        }

        return repository
                .findByUnitMeasurementAndEnName(
                        typeOfferRequest.unitMeasurement(),
                        typeOfferRequest.enName()
                );
    }
}
