package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.UnitMeasurementResponse;
import org.university.payment_for_utilities.repositories.service_information_institutions.UnitMeasurementRepository;
import org.university.payment_for_utilities.services.implementations.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.UnitMeasurementService;

@Service
public class UnitMeasurementServiceImpl extends TransliterationService<UnitMeasurement, UnitMeasurementRepository> implements UnitMeasurementService {
    protected UnitMeasurementServiceImpl(UnitMeasurementRepository repository) {
        super(repository, "Units measurement");
    }

    @Override
    protected UnitMeasurement createEntity(Request request) {
        return (UnitMeasurement) initTransliterationPropertyBuilder(UnitMeasurement.builder(), request);
    }

    @Override
    protected UnitMeasurement createEntity(Response response) {
        return (UnitMeasurement) initTransliterationPropertyBuilder(UnitMeasurement.builder(), response);
    }

    @Override
    protected Response createResponse(@NonNull UnitMeasurement entity) {
        return initResponseBuilder(UnitMeasurementResponse.builder(), entity);
    }
}
