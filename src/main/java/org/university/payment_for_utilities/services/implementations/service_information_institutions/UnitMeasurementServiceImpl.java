package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.UnitMeasurementRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
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
        var unitRequest = (UnitMeasurementRequest) request;
        return UnitMeasurement
                .builder()
                .uaName(unitRequest.uaName())
                .enName(unitRequest.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected UnitMeasurement createEntity(Response response) {
        var unitResponse = (UnitMeasurementResponse) response;
        return UnitMeasurement
                .builder()
                .id(unitResponse.id())
                .uaName(unitResponse.uaName())
                .enName(unitResponse.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull UnitMeasurement entity) {
        return UnitMeasurementResponse
                .builder()
                .id(entity.getId())
                .uaName(entity.getUaName())
                .enName(entity.getEnName())
                .build();
    }
}
