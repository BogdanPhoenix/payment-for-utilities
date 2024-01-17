package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.address.TypeSettlementRepository;
import org.university.payment_for_utilities.services.implementations.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@Service
public class TypeSettlementServiceImpl extends TransliterationService<TypeSettlement, TypeSettlementRepository> implements TypeSettlementService {
    @Autowired
    public TypeSettlementServiceImpl(TypeSettlementRepository repository){
        super(repository, "Types settlement");
    }

    @Override
    protected TypeSettlement createEntity(@NonNull Request request) {
        return (TypeSettlement) initTransliterationPropertyBuilder(TypeSettlement.builder(), request);
    }

    @Override
    protected TypeSettlement createEntity(Response response) {
        return (TypeSettlement) initTransliterationPropertyBuilder(TypeSettlement.builder(), response);
    }

    @Override
    protected Response createResponse(@NonNull TypeSettlement entity) {
        return initResponseBuilder(TypeSettlementResponse.builder(), entity);
    }
}
