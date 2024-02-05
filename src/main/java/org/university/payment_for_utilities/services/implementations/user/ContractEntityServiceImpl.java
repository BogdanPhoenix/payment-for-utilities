package org.university.payment_for_utilities.services.implementations.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.user.ContractEntityRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.company.CompanyTariffRepository;
import org.university.payment_for_utilities.repositories.user.ContractEntityRepository;
import org.university.payment_for_utilities.repositories.user.RegisteredUserRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.receipt.ReceiptService;
import org.university.payment_for_utilities.services.interfaces.user.ContractEntityService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class ContractEntityServiceImpl extends CrudServiceAbstract<ContractEntity, ContractEntityRepository> implements ContractEntityService {
    private static final String NUM_CONTRACT_TEMPLATE = "^\\d{11}$";

    private final ReceiptService receiptService;
    private final RegisteredUserRepository registeredUserRepository;
    private final CompanyTariffRepository companyTariffRepository;

    @Autowired
    public ContractEntityServiceImpl(
            ContractEntityRepository repository,
            ReceiptService receiptService,
            RegisteredUserRepository registeredUserRepository,
            CompanyTariffRepository companyTariffRepository
    ) {
        super(repository, "Contract entities");

        this.receiptService = receiptService;
        this.registeredUserRepository = registeredUserRepository;
        this.companyTariffRepository = companyTariffRepository;
    }

    @Override
    protected ContractEntity createEntity(Request request) {
        var contractRequest = (ContractEntityRequest) request;
        var registeredUser = getRegisteredUser(contractRequest.getRegisteredUser());
        var companyTariff = getCompanyTariff(contractRequest.getCompanyTariff());

        return ContractEntity
                .builder()
                .registeredUser(registeredUser)
                .companyTariff(companyTariff)
                .numContract(contractRequest.getNumContract())
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull ContractEntity entity) {
        deactivateChildrenCollection(entity.getReceipts(), receiptService);
    }

    @Override
    protected void updateEntity(@NonNull ContractEntity entity, @NonNull Request request) {
        var newValue = (ContractEntityRequest) request;

        if(!newValue.getRegisteredUser().equals(Response.EMPTY_PARENT_ENTITY)){
            var registeredUser = getRegisteredUser(newValue.getRegisteredUser());
            entity.setRegisteredUser(registeredUser);
        }
        if(!newValue.getCompanyTariff().equals(Response.EMPTY_PARENT_ENTITY)){
            var companyTariff = getCompanyTariff(newValue.getCompanyTariff());
            entity.setCompanyTariff(companyTariff);
        }
        if(!newValue.getNumContract().isBlank()){
            entity.setNumContract(newValue.getNumContract());
        }
    }

    private @NonNull RegisteredUser getRegisteredUser(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(registeredUserRepository, id);
    }

    private @NonNull CompanyTariff getCompanyTariff(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(companyTariffRepository, id);
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var contractRequest = (ContractEntityRequest) request;
        validateNumContract(contractRequest.getNumContract());
    }

    private void validateNumContract(@NonNull String numContract) throws InvalidInputDataException {
        if(isNumContract(numContract)){
            return;
        }

        var message = String.format("Contract number: \"%s\" has not been validated. It should contain only eleven digits.", numContract);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isNumContract(@NonNull String numContract){
        return numContract.isBlank() || numContract
                .matches(NUM_CONTRACT_TEMPLATE);
    }

    @Override
    protected Optional<ContractEntity> findEntity(@NonNull Request request) {
        var contractRequest = (ContractEntityRequest) request;
        return repository
                .findByNumContract(
                        contractRequest.getNumContract()
                );
    }
}
