package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.District;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.address.DistrictResponse;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
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
        var builder = District.builder();
        super.initTransliterationPropertyBuilder(builder, request);
        return builder.build();
    }

    @Override
    protected District createEntity(@NonNull Response response){
        var builder = District.builder();
        super.initTransliterationPropertyBuilder(builder, response);
        return builder.build();
    }

    @Override
    protected Response createResponse(@NonNull District entity){
        var builder = DistrictResponse.builder();
        super.initResponseBuilder(builder, entity);
        return builder.build();
    }
}
