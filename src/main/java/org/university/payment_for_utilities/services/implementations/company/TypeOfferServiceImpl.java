package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.TypeOffer;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.company.TypeOfferRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.UnitMeasurementRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.company.CompanyTariffService;
import org.university.payment_for_utilities.services.interfaces.company.TypeOfferService;

import java.util.Optional;

@Service
public class TypeOfferServiceImpl extends TransliterationService<TypeOffer, TypeOfferRepository> implements TypeOfferService {
    private final CompanyTariffService companyTariffService;
    private final UnitMeasurementRepository unitMeasurementRepository;

    @Autowired
    public TypeOfferServiceImpl(
            TypeOfferRepository repository,
            CompanyTariffService companyTariffService,
            UnitMeasurementRepository unitMeasurementRepository
    ) {
        super(repository, "Type offers");

        this.companyTariffService = companyTariffService;
        this.unitMeasurementRepository = unitMeasurementRepository;
    }

    @Override
    protected TypeOffer createEntity(Request request) {
        var builder = TypeOffer.builder();
        var typeOfferRequest = (TypeOfferRequest) request;
        var unitMeasurement = getUnitMeasurement(typeOfferRequest.getUnitMeasurement());

        return super
                .initTransliterationPropertyBuilder(builder, request)
                .unitMeasurement(unitMeasurement)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull TypeOffer entity) {
        deactivateChildrenCollection(entity.getTariffs(), companyTariffService);
    }

    @Override
    protected Optional<TypeOffer> findEntity(@NonNull Request request) {
        var typeOfferRequest = (TypeOfferRequest) request;
        var unitMeasurement = getUnitMeasurement(typeOfferRequest.getUnitMeasurement());

        return repository
                .findByUnitMeasurementAndEnName(
                        unitMeasurement,
                        typeOfferRequest.getEnName()
                );
    }

    private @NonNull UnitMeasurement getUnitMeasurement(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(unitMeasurementRepository, id);
    }
}
