package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.address.SettlementNameResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.SettlementNameRepository;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;

@Service
public class SettlementNameServiceImpl extends TransliterationService<SettlementName, SettlementNameRepository> implements SettlementNameService {
    @Autowired
    public SettlementNameServiceImpl(SettlementNameRepository repository){
        super(repository, "Names administrative units");
    }

    @Override
    protected SettlementName createEntity(@NonNull Request request) {
        var settlementNameRequest = (SettlementNameRequest) request;
        return SettlementName
                .builder()
                .uaName(settlementNameRequest.uaName())
                .enName(settlementNameRequest.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected SettlementName createEntity(Response response) {
        var settlementNameResponse = (SettlementNameResponse) response;
        return SettlementName
                .builder()
                .id(settlementNameResponse.id())
                .uaName(settlementNameResponse.uaName())
                .enName(settlementNameResponse.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull SettlementName entity) {
        return SettlementNameResponse
                .builder()
                .id(entity.getId())
                .uaName(entity.getUaName())
                .enName(entity.getEnName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull SettlementName entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (SettlementNameRequest) updateRequest.getOldValue();
        var newValue = (SettlementNameRequest) updateRequest.getNewValue();

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
