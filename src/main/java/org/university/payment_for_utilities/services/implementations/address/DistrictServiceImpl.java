package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.District;
import org.university.payment_for_utilities.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.address.DistrictResponse;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.DistrictRepository;
import org.university.payment_for_utilities.services.implementations.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

@Service
public class DistrictServiceImpl extends TransliterationService<District, DistrictRepository> implements DistrictService {
    @Autowired
    public DistrictServiceImpl(DistrictRepository repository){
        super(repository, "Districts");
    }

    @Override
    protected District createEntity(@NonNull Request request){
        var districtRequest = (DistrictRequest) request;
        return District
                .builder()
                .uaName(districtRequest.getUaName())
                .enName(districtRequest.getEnName())
                .currentData(true)
                .build();
    }

    @Override
    protected District createEntity(@NonNull Response response){
        var districtResponse = (DistrictResponse) response;
        return District
                .builder()
                .id(districtResponse.getId())
                .uaName(districtResponse.getUaName())
                .enName(districtResponse.getEnName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull District oblast){
        return DistrictResponse
                .builder()
                .id(oblast.getId())
                .uaName(oblast.getUaName())
                .enName(oblast.getEnName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull District entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (DistrictRequest) updateRequest.getOldValue();
        var newValue = (DistrictRequest) updateRequest.getNewValue();

        entity.setUaName(
                updateAttribute(
                        oldValue.getUaName(),
                        newValue.getUaName()
                )
        );

        entity.setEnName(
                updateAttribute(
                        oldValue.getEnName(),
                        newValue.getEnName()
                )
        );
    }
}
