package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.company.CompanyPhoneNumRepository;
import org.university.payment_for_utilities.repositories.company.CompanyRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.PhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.WorkingWithPhoneNumAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.PhoneNumService;

import java.util.Optional;

@Service
public class CompanyPhoneNumServiceImpl extends WorkingWithPhoneNumAbstract<CompanyPhoneNum, CompanyPhoneNumRepository> implements CompanyPhoneNumService {
    private final PhoneNumService phoneNumService;
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyPhoneNumServiceImpl(
            CompanyPhoneNumRepository repository,
            PhoneNumService phoneNumService,
            PhoneNumRepository phoneNumRepository,
            CompanyRepository companyRepository
    ) {
        super(repository, "Company phone nums", phoneNumRepository);
        this.phoneNumService = phoneNumService;
        this.companyRepository = companyRepository;
    }

    @Override
    protected CompanyPhoneNum createEntity(Request request) {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;
        var company = getCompany(companyPhoneNumRequest.getCompany().getId());
        var phoneNum = getPhoneNum(companyPhoneNumRequest.getPhoneNum().getId());

        return CompanyPhoneNum
                .builder()
                .company(company)
                .phoneNum(phoneNum)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull CompanyPhoneNum entity) {
        deactivateChild(entity.getPhoneNum(), phoneNumService);
    }

    @Override
    protected void updateEntity(@NonNull CompanyPhoneNum entity, @NonNull Request request) {
        var newValue = (CompanyPhoneNumRequest) request;

        if(!newValue.getCompany().isEmpty()){
            var company = getCompany(newValue.getCompany().getId());
            entity.setCompany(company);
        }
        if (!newValue.getPhoneNum().isEmpty()){
            var phoneNum = getPhoneNum(newValue.getPhoneNum().getId());
            entity.setPhoneNum(phoneNum);
        }
    }

    @Override
    protected Optional<CompanyPhoneNum> findEntity(@NonNull Request request) {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;
        var company = getCompany(companyPhoneNumRequest.getCompany().getId());
        var phoneNum = getPhoneNum(companyPhoneNumRequest.getPhoneNum().getId());

        return repository.findByCompanyAndPhoneNum(company, phoneNum);
    }
    private @NonNull Company getCompany(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(companyRepository, id);
    }
}
