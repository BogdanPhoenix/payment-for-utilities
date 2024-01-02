package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.Oblast;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.domains.pojo.responses.address.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.OblastRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;
import org.university.payment_for_utilities.domains.pojo.responses.address.OblastResponse;

import java.util.Optional;

@Slf4j
@Service
public class OblastServiceImpl extends CrudServiceAbstract<Oblast, OblastRepository> implements OblastService {
    @Autowired
    public OblastServiceImpl(OblastRepository repository){
        this.repository = repository;
        this.tableName = "Oblasts";
    }

    @Override
    protected boolean isDuplicate(@NonNull Request request) {
        var entity = repository.findByEnName(request.getEnName());
        return entity.isPresent();
    }

    @Override
    protected Oblast createEntity(@NonNull Request request){
        return Oblast
                .builder()
                .uaName(request.getUaName())
                .enName(request.getEnName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Oblast entity){
        return OblastResponse
                .builder()
                .id(entity.getId())
                .uaName(entity.getUaName())
                .enName(entity.getEnName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Oblast entity, @NonNull UpdateRequest updateRequest) {
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
    protected Optional<Oblast> findOldEntity(@NonNull UpdateRequest updateRequest){
        return repository
                .findByEnName(updateRequest
                        .getOldValue()
                        .getEnName()
                );
    }
}
