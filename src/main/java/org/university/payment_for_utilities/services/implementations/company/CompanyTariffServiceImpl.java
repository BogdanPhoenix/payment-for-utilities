package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.company.CompanyTariffRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;
import org.university.payment_for_utilities.repositories.company.CompanyTariffRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyTariffService;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CompanyTariffServiceImpl extends CrudServiceAbstract<CompanyTariff, CompanyTariffRepository> implements CompanyTariffService {
    private static final String FIXED_COST_TEMPLATE = "^\\d+\\.\\d{1,2}$";
    private static final int FRACTIONAL_ACCURACY = 2;
    private static final String COMPLEMENT_SYMBOL = "0";

    protected CompanyTariffServiceImpl(CompanyTariffRepository repository) {
        super(repository, "Company tariffs");
    }

    @Override
    protected CompanyTariff createEntity(Request request) {
        var companyTariffRequest = (CompanyTariffRequest) request;
        var fixedCost = convertStringToBigDecimal(companyTariffRequest.getFixedCost());

        return CompanyTariff
                .builder()
                .company(companyTariffRequest.getCompany())
                .type(companyTariffRequest.getType())
                .name(companyTariffRequest.getName())
                .fixedCost(fixedCost)
                .build();
    }

    @Override
    protected CompanyTariff createEntity(Response response) {
        var companyTariffResponse = (CompanyTariffResponse) response;
        var builder = CompanyTariff.builder();
        initEntityBuilder(builder, response);

        return builder
                .company(companyTariffResponse.getCompany())
                .type(companyTariffResponse.getType())
                .name(companyTariffResponse.getName())
                .fixedCost(companyTariffResponse.getFixedCost())
                .build();
    }

    @Override
    protected Response createResponse(CompanyTariff entity) {
        var builder = CompanyTariffResponse.builder();
        initResponseBuilder(builder, entity);

        return builder
                .company(entity.getCompany())
                .type(entity.getType())
                .name(entity.getName())
                .fixedCost(entity.getFixedCost())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull CompanyTariff entity, @NonNull Request request) {
        var newValue = (CompanyTariffRequest) request;

        if(!newValue.getCompany().isEmpty()){
            entity.setCompany(newValue.getCompany());
        }
        if(!newValue.getType().isEmpty()){
            entity.setType(newValue.getType());
        }
        if(!newValue.getName().isBlank()){
            entity.setName(newValue.getName());
        }
        if(!newValue.getFixedCost().isBlank()){
            var fixedCost = convertStringToBigDecimal(newValue.getFixedCost());
            entity.setFixedCost(fixedCost);
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var companyTariffRequest = (CompanyTariffRequest) request;

        validateName(companyTariffRequest.getName());
        validateFixedCost(companyTariffRequest.getFixedCost());
    }

    private void validateFixedCost(@NonNull String fixedCost) throws InvalidInputDataException {
        if(isFixedCost(fixedCost)){
            return;
        }

        var message = String.format("Fixed value provided by you: \"%s\" has not been validated. It should contain only a fractional number and should be separated by a period.", fixedCost);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isFixedCost(@NonNull String fixedCost) {
        return fixedCost.isBlank() || fixedCost
                .matches(FIXED_COST_TEMPLATE);
    }

    @Override
    protected Optional<CompanyTariff> findEntity(@NonNull Request request) {
        var companyTariffRequest = (CompanyTariffRequest) request;
        return repository
                .findByCompanyAndName(
                        companyTariffRequest.getCompany(),
                        companyTariffRequest.getName()
                );
    }

    private @NonNull BigDecimal convertStringToBigDecimal(@NonNull String money) {
        int indexPoint = money.lastIndexOf(".");
        int fractionalValue = money.length() - indexPoint - 1;

        if(fractionalValue < FRACTIONAL_ACCURACY) {
            money += COMPLEMENT_SYMBOL.repeat(FRACTIONAL_ACCURACY - fractionalValue);
        }

        return new BigDecimal(money);
    }
}
