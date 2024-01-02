package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.District;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.domains.pojo.responses.address.DistrictResponse;
import org.university.payment_for_utilities.domains.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.DistrictRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl extends CrudServiceAbstract<District, DistrictRepository> implements DistrictService {
    @Autowired
    public DistrictServiceImpl(DistrictRepository repository){
        this.repository = repository;
        this.tableName = "Districts";
    }

    @Override
    protected boolean isDuplicate(@NonNull Request request) {
        var entity = repository.findByEnName(request.getEnName());
        return entity.isPresent();
    }

    @Override
    protected District createEntity(@NonNull Request request){
        return District
                .builder()
                .uaName(request.getUaName())
                .enName(request.getEnName())
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
        var oldValue = updateRequest.getOldValue();
        var newValue = updateRequest.getNewValue();

        entity.setUaName(
                updatePrimitive(
                        oldValue.getUaName(),
                        newValue.getUaName()
                )
        );

        entity.setEnName(
                updatePrimitive(
                        oldValue.getEnName(),
                        newValue.getEnName()
                )
        );
    }

    @Override
    protected Optional<District> findOldEntity(@NonNull UpdateRequest updateRequest) {
        return repository
                .findByEnName(updateRequest
                        .getOldValue()
                        .getEnName()
                );
    }
}
