package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.address.TypeSettlementRepository;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@Service
public class TypeSettlementServiceImpl extends TransliterationService<TypeSettlement, TypeSettlementRepository> implements TypeSettlementService {
    private final SettlementService settlementService;

    @Autowired
    public TypeSettlementServiceImpl(
            TypeSettlementRepository repository,
            SettlementService settlementService
    ){
        super(repository, "Types settlement");
        this.settlementService = settlementService;
    }

    @Override
    protected TypeSettlement createEntity(@NonNull Request request) {
        var builder = TypeSettlement.builder();
        return super
                .initTransliterationPropertyBuilder(builder, request)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull TypeSettlement entity) {
        deactivateChildrenCollection(entity.getSettlements(), settlementService);
    }
}
