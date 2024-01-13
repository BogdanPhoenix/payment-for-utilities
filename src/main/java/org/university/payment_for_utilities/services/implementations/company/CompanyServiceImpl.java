package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
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
    protected void updateEntity(@NonNull Company entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (CompanyRequest) updateRequest.getOldValue();
        var newValue = (CompanyRequest) updateRequest.getNewValue();

        entity.setAddress(
                updateAttribute(
                        oldValue.address(),
                        newValue.address()
                )
        );
        entity.setEdrpou(
                updateAttribute(
                        oldValue.edrpou(),
                        newValue.edrpou()
                )
        );
        entity.setWebsite(
                updateAttribute(
                        oldValue.website(),
                        newValue.website()
                )
        );
        entity.setName(
                updateAttribute(
                        oldValue.name(),
                        newValue.name()
                )
        );
        entity.setCurrentAccount(
                updateAttribute(
                        oldValue.currentAccount(),
                        newValue.currentAccount()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var companyRequest = (CompanyRequest) request;

        validateName(companyRequest.name());
        validateCurrentAccount(companyRequest.currentAccount());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (CompanyRequest) updateRequest.getOldValue();
        var newValue = (CompanyRequest) updateRequest.getNewValue();

        validateName(oldValue.name());
        validateName(newValue.name());

        validateCurrentAccount(oldValue.currentAccount());
        validateCurrentAccount(newValue.currentAccount());
    }

    private void validateCurrentAccount(@NonNull String currentAccount) throws InvalidInputDataException {
        if (isCurrentAccount(currentAccount)){
            return;
        }

        var message = String.format("The current account you provided: \"%s\" company has not been validated. It must contain exactly fourteen digits.", currentAccount);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isCurrentAccount(@NonNull String currentAccount) {
        return currentAccount
                .matches(CURRENT_ACCOUNT_TEMPLATE);
    }

    @Override
    protected Optional<Company> findOldEntity(@NonNull Request request) {
        var companyRequest = (CompanyRequest) request;
        return repository
                .findByCurrentAccount(
                        companyRequest.currentAccount()
                );
    }
}
