package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.repositories.company.CompanyRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyService;

import java.util.Optional;

@Service
public class CompanyServiceImpl extends CrudServiceAbstract<Company, CompanyRepository> implements CompanyService {
    private static final String CURRENT_ACCOUNT_TEMPLATE = "^\\d{14}$";
    protected CompanyServiceImpl(CompanyRepository repository) {
        super(repository, "Companies");
    }

    @Override
    protected Company createEntity(Request request) {
        var companyRequest = (CompanyRequest) request;
        return Company
                .builder()
                .address(companyRequest.address())
                .edrpou(companyRequest.edrpou())
                .website(companyRequest.website())
                .name(companyRequest.name())
                .currentAccount(companyRequest.currentAccount())
                .currentData(true)
                .build();
    }

    @Override
    protected Company createEntity(Response response) {
        var companyResponse = (CompanyResponse) response;
        return Company
                .builder()
                .id(companyResponse.id())
                .address(companyResponse.address())
                .edrpou(companyResponse.edrpou())
                .website(companyResponse.website())
                .name(companyResponse.name())
                .currentAccount(companyResponse.currentAccount())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Company entity) {
        return CompanyResponse
                .builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .edrpou(entity.getEdrpou())
                .website(entity.getWebsite())
                .name(entity.getName())
                .currentAccount(entity.getCurrentAccount())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Company entity, @NonNull Request request) {
        var newValue = (CompanyRequest) request;

        if(!newValue.address().isEmpty()){
            entity.setAddress(newValue.address());
        }
        if(!newValue.edrpou().isEmpty()){
            entity.setEdrpou(newValue.edrpou());
        }
        if(!newValue.website().isEmpty()){
            entity.setWebsite(newValue.website());
        }
        if(!newValue.name().isBlank()){
            entity.setName(newValue.name());
        }
        if(!newValue.currentAccount().isBlank()){
            entity.setCurrentAccount(newValue.currentAccount());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var companyRequest = (CompanyRequest) request;

        validateName(companyRequest.name());
        validateCurrentAccount(companyRequest.currentAccount());
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
                        companyRequest.currentAccount()
                );
    }
}
