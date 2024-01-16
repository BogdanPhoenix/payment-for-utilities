package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyPhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.company.CompanyPhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.WorkingWithPhoneNumAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;

import java.util.Optional;

@Service
public class CompanyPhoneNumServiceImpl extends WorkingWithPhoneNumAbstract<CompanyPhoneNum, CompanyPhoneNumRepository> implements CompanyPhoneNumService {
    protected CompanyPhoneNumServiceImpl(CompanyPhoneNumRepository repository) {
        super(repository, "Company phone nums");
    }

    @Override
    protected CompanyPhoneNum createEntity(Request request) {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;
        return CompanyPhoneNum
                .builder()
                .company(companyPhoneNumRequest.company())
                .phoneNum(companyPhoneNumRequest.phoneNum())
                .currentData(true)
                .build();
    }

    @Override
    protected CompanyPhoneNum createEntity(Response response) {
        var companyPhoneNumResponse = (CompanyPhoneNumResponse) response;
        return CompanyPhoneNum
                .builder()
                .id(companyPhoneNumResponse.id())
                .company(companyPhoneNumResponse.company())
                .phoneNum(companyPhoneNumResponse.phoneNum())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull CompanyPhoneNum entity) {
        return CompanyPhoneNumResponse
                .builder()
                .id(entity.getId())
                .company(entity.getCompany())
                .phoneNum(entity.getPhoneNum())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull CompanyPhoneNum entity, @NonNull Request request) {
        var newValue = (CompanyPhoneNumRequest) request;

        if(!newValue.company().isEmpty()){
            entity.setCompany(newValue.company());
        }
        if (!newValue.phoneNum().isEmpty()){
            entity.setPhoneNum(newValue.phoneNum());
        }
    }

    @Override
    protected Optional<CompanyPhoneNum> findEntity(@NonNull Request request) {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;
        return repository
                .findByCompanyAndPhoneNum(
                        companyPhoneNumRequest.company(),
                        companyPhoneNumRequest.phoneNum()
                );
    }
}
