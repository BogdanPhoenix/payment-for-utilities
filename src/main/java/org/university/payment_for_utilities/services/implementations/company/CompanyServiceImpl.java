package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.address.AddressResidenceRepository;
import org.university.payment_for_utilities.repositories.company.CompanyRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.EdrpouRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.WebsiteRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.CommonInstitutionalDataService;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;
import org.university.payment_for_utilities.services.interfaces.company.CompanyService;
import org.university.payment_for_utilities.services.interfaces.company.CompanyTariffService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class CompanyServiceImpl extends CommonInstitutionalDataService<Company, CompanyRepository> implements CompanyService {
    private static final String CURRENT_ACCOUNT_TEMPLATE = "^\\d{14}$";

    private final CompanyPhoneNumService companyPhoneNumService;
    private final CompanyTariffService companyTariffService;
    private final AddressResidenceRepository addressResidenceRepository;

    @Autowired
    public CompanyServiceImpl(
            CompanyRepository repository,
            EdrpouService edrpouService,
            EdrpouRepository edrpouRepository,
            WebsiteService websiteService,
            WebsiteRepository websiteRepository,
            CompanyPhoneNumService companyPhoneNumService,
            CompanyTariffService companyTariffService,
            AddressResidenceRepository addressResidenceRepository
    ) {
        super(
                repository,
                "Companies",
                edrpouService,
                edrpouRepository,
                websiteService,
                websiteRepository
        );

        this.companyPhoneNumService = companyPhoneNumService;
        this.companyTariffService = companyTariffService;
        this.addressResidenceRepository = addressResidenceRepository;
    }

    @Override
    protected Company createEntity(Request request) {
        var builder = Company.builder();
        var companyRequest = (CompanyRequest) request;
        var address = getAddress(companyRequest.getAddress().getId());

        return super
                .initCommonInstitutionalDataBuilder(builder, companyRequest)
                .address(address)
                .currentAccount(companyRequest.getCurrentAccount())
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull Company entity) {
        super.deactivatedChildren(entity);
        deactivateChildrenCollection(entity.getPhones(), companyPhoneNumService);
        deactivateChildrenCollection(entity.getTariffs(), companyTariffService);
    }

    @Override
    protected void updateEntity(@NonNull Company entity, @NonNull Request request) {
        super.updateEntity(entity, request);
        var newValue = (CompanyRequest) request;

        if(!newValue.getAddress().isEmpty()){
            var address = getAddress(newValue.getAddress().getId());
            entity.setAddress(address);
        }
        if(!newValue.getCurrentAccount().isBlank()){
            entity.setCurrentAccount(newValue.getCurrentAccount());
        }
    }

    private @NonNull AddressResidence getAddress(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(addressResidenceRepository, id);
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        super.validationProcedureRequest(request);
        var companyRequest = (CompanyRequest) request;

        validateCurrentAccount(companyRequest.getCurrentAccount());
    }

    private void validateCurrentAccount(@NonNull String currentAccount) throws InvalidInputDataException {
        if (isCurrentAccount(currentAccount)){
            return;
        }

        var message = String.format("The current account you provided: \"%s\" company has not been validated. It must contain exactly fourteen digits.", currentAccount);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isCurrentAccount(@NonNull String currentAccount) {
        return currentAccount.isBlank() ||
                currentAccount
                .matches(CURRENT_ACCOUNT_TEMPLATE);
    }

    @Override
    protected Optional<Company> findEntity(@NonNull Request request) {
        var companyRequest = (CompanyRequest) request;
        return repository
                .findByCurrentAccount(
                        companyRequest.getCurrentAccount()
                );
    }
}
