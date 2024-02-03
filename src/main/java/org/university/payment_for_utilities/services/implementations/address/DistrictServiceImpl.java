package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.District;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.address.DistrictRepository;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.TransliterationService;
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
        return super
                .initTransliterationPropertyBuilder(builder, request)
                .build();
    }
}
