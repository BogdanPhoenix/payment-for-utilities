package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.address.SettlementNameResponse;
import org.university.payment_for_utilities.repositories.address.SettlementNameRepository;
import org.university.payment_for_utilities.services.implementations.TransliterationService;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;

@Service
public class SettlementNameServiceImpl extends TransliterationService<SettlementName, SettlementNameRepository> implements SettlementNameService {
    @Autowired
    public SettlementNameServiceImpl(SettlementNameRepository repository){
        super(repository, "Names administrative units");
    }

    @Override
    protected SettlementName createEntity(@NonNull Request request) {
        var builder = SettlementName.builder();
        super.initTransliterationPropertyBuilder(builder, request);
        return builder.build();
    }

    @Override
    protected SettlementName createEntity(Response response) {
        var builder = SettlementName.builder();
        super.initTransliterationPropertyBuilder(builder, response);
        return builder.build();
    }

    @Override
    protected Response createResponse(@NonNull SettlementName entity) {
        var builder = SettlementNameResponse.builder();
        super.initResponseBuilder(builder, entity);
        return builder.build();
    }
}
