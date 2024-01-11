package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.address.TypeSettlementRepository;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@Service
public class TypeSettlementServiceImpl extends TransliterationService<TypeSettlement, TypeSettlementRepository> implements TypeSettlementService {
    @Autowired
    public TypeSettlementServiceImpl(TypeSettlementRepository repository){
        super(repository, "Types settlement");
    }

    @Override
    protected TypeSettlement createEntity(@NonNull Request request) {
        var typeRequest = (TypeSettlementRequest) request;
        return TypeSettlement
                .builder()
                .uaName(typeRequest.uaName())
                .enName(typeRequest.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected TypeSettlement createEntity(Response response) {
        var typeResponse = (TypeSettlementResponse) response;
        return TypeSettlement
                .builder()
                .id(typeResponse.id())
                .uaName(typeResponse.uaName())
                .enName(typeResponse.enName())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull TypeSettlement entity) {
        return TypeSettlementResponse
                .builder()
                .id(entity.getId())
                .uaName(entity.getUaName())
                .enName(entity.getEnName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull TypeSettlement entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (TypeSettlementRequest) updateRequest.getOldValue();
        var newValue = (TypeSettlementRequest) updateRequest.getNewValue();

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
