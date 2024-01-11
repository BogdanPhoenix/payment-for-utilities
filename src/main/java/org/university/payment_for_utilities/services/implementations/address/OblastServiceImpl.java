package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.Oblast;
import org.university.payment_for_utilities.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.OblastRepository;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;
import org.university.payment_for_utilities.pojo.responses.address.OblastResponse;

@Service
public class OblastServiceImpl extends TransliterationService<Oblast, OblastRepository> implements OblastService {
    @Autowired
    public OblastServiceImpl(OblastRepository repository){
        super(repository, "Oblasts");
    }

    @Override
    protected Oblast createEntity(@NonNull Request request){
        var oblastRequest = (OblastRequest) request;
        return Oblast
                .builder()
                .uaName(oblastRequest.uaName())
                .enName(oblastRequest.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected Oblast createEntity(Response response) {
        var oblastResponse = (OblastResponse) response;
        return Oblast
                .builder()
                .id(oblastResponse.id())
                .uaName(oblastResponse.uaName())
                .enName(oblastResponse.enName())
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
        var oldValue = (OblastRequest) updateRequest.getOldValue();
        var newValue = (OblastRequest) updateRequest.getNewValue();

        entity.setUaName(
                updateAttribute(
                        oldValue.uaName(),
                        newValue.uaName()
                )
        );

        entity.setEnName(
                updateAttribute(
                        oldValue.enName(),
                        newValue.enName()
                )
        );
    }
}
