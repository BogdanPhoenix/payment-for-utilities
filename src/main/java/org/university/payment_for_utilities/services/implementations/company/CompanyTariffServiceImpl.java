package org.university.payment_for_utilities.services.implementations.company;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.domains.company.TypeOffer;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.company.CompanyTariffRequest;
import org.university.payment_for_utilities.repositories.company.CompanyRepository;
import org.university.payment_for_utilities.repositories.company.CompanyTariffRepository;
import org.university.payment_for_utilities.repositories.company.TypeOfferRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.company.CompanyTariffService;
import org.university.payment_for_utilities.services.interfaces.user.ContractEntityService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.convertStringToBigDecimal;
import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.validateFinance;

@Service
public class CompanyTariffServiceImpl extends CrudServiceAbstract<CompanyTariff, CompanyTariffRepository> implements CompanyTariffService {
    private final ContractEntityService contractEntityService;
    private final CompanyRepository companyRepository;
    private final TypeOfferRepository typeOfferRepository;

    @Autowired
    public CompanyTariffServiceImpl(
            CompanyTariffRepository repository,
            ContractEntityService contractEntityService,
            CompanyRepository companyRepository,
            TypeOfferRepository typeOfferRepository
    ) {
        super(repository, "Company tariffs");

        this.contractEntityService = contractEntityService;
        this.companyRepository = companyRepository;
        this.typeOfferRepository = typeOfferRepository;
    }

    @Override
    protected CompanyTariff createEntity(Request request) {
        var companyTariffRequest = (CompanyTariffRequest) request;
        var company = getCompany(companyTariffRequest.getCompany().getId());
        var type = getType(companyTariffRequest.getType().getId());
        var fixedCost = convertStringToBigDecimal(companyTariffRequest.getFixedCost());

        return CompanyTariff
                .builder()
                .company(company)
                .type(type)
                .name(companyTariffRequest.getName())
                .fixedCost(fixedCost)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull CompanyTariff entity) {
        deactivateChildrenCollection(entity.getContractEntities(), contractEntityService);
    }

    @Override
    protected void updateEntity(@NonNull CompanyTariff entity, @NonNull Request request) {
        var newValue = (CompanyTariffRequest) request;

        if(!newValue.getCompany().isEmpty()){
            var company = getCompany(newValue.getCompany().getId());
            entity.setCompany(company);
        }
        if(!newValue.getType().isEmpty()){
            var type = getType(newValue.getType().getId());
            entity.setType(type);
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
        validateFinance("fixed cost", companyTariffRequest.getFixedCost());
    }

    @Override
    protected Optional<CompanyTariff> findEntity(@NonNull Request request) {
        var companyTariffRequest = (CompanyTariffRequest) request;
        var company = getCompany(companyTariffRequest.getCompany().getId());

        return repository
                .findByCompanyAndName(
                        company,
                        companyTariffRequest.getName()
                );
    }

    private @NonNull Company getCompany(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(companyRepository, id);
    }

    private @NonNull TypeOffer getType(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(typeOfferRepository, id);
    }
}
