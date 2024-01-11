package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyPhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.company.CompanyPhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.PhoneNumServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;

import java.util.Optional;

@Slf4j
@Service
public class CompanyPhoneNumServiceImpl extends PhoneNumServiceAbstract<CompanyPhoneNum, CompanyPhoneNumRepository> implements CompanyPhoneNumService {
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
    protected void updateEntity(@NonNull CompanyPhoneNum entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (CompanyPhoneNumRequest) updateRequest.getOldValue();
        var newValue = (CompanyPhoneNumRequest) updateRequest.getNewValue();

        entity.setCompany(
                updateAttribute(
                        oldValue.company(),
                        newValue.company()
                )
        );
        entity.setPhoneNum(
                updateAttribute(
                        oldValue.phoneNum(),
                        newValue.phoneNum()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;

        validateCompany(companyPhoneNumRequest.company());
        validatePhoneNum(companyPhoneNumRequest.phoneNum());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (CompanyPhoneNumRequest) updateRequest.getOldValue();
        var newValue = (CompanyPhoneNumRequest) updateRequest.getNewValue();

        validatePhoneNum(oldValue.phoneNum());
        validatePhoneNum(newValue.phoneNum());
    }

    private void validateCompany(Company company) throws InvalidInputDataException {
        if(isValidCompany(company)){
            return;
        }

        var message = String.format("The company entity you provided has not been validated: \"%s\". The company entity cannot be null or empty.", company);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isValidCompany(@NonNull Company company) {
        return !company.isEmpty();
    }

    @Override
    protected Optional<CompanyPhoneNum> findOldEntity(@NonNull Request request) {
        var companyPhoneNumRequest = (CompanyPhoneNumRequest) request;
        return repository
                .findByPhoneNum(
                        companyPhoneNumRequest.phoneNum()
                );
    }
}
