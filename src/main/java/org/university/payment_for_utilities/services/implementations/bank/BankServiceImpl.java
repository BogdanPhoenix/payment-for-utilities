package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.bank.BankRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankService;

import java.util.Optional;

@Service
public class BankServiceImpl extends CrudServiceAbstract<Bank, BankRepository> implements BankService {
    private static final String MFO_TEMPLATE = "^\\d{6}$";

    protected BankServiceImpl(BankRepository repository) {
        super(repository, "Banks");
    }

    @Override
    protected Bank createEntity(Request request) {
        var bankRequest = (BankRequest) request;
        return Bank
                .builder()
                .name(bankRequest.getName())
                .website(bankRequest.getWebsite())
                .edrpou(bankRequest.getEdrpou())
                .mfo(bankRequest.getMfo())
                .build();
    }

    @Override
    protected Bank createEntity(Response response) {
        var bankResponse = (BankResponse) response;
        var builder = Bank.builder();
        initEntityBuilder(builder, response);

        return builder
                .name(bankResponse.getName())
                .website(bankResponse.getWebsite())
                .edrpou(bankResponse.getEdrpou())
                .mfo(bankResponse.getMfo())
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Bank entity) {
        var builder = BankResponse.builder();
        initResponseBuilder(builder, entity);

        return builder
                .name(entity.getName())
                .website(entity.getWebsite())
                .edrpou(entity.getEdrpou())
                .mfo(entity.getMfo())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Bank entity, @NonNull Request request) {
        var newValue = (BankRequest) request;

        if(!newValue.getName().isBlank()){
            entity.setName(newValue.getName());
        }
        if(!newValue.getWebsite().isEmpty()){
            entity.setWebsite(newValue.getWebsite());
        }
        if(!newValue.getEdrpou().isEmpty()){
            entity.setEdrpou(newValue.getEdrpou());
        }
        if(!newValue.getMfo().isBlank()){
            entity.setMfo(newValue.getMfo());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var bankRequest = (BankRequest) request;

        validateName(bankRequest.getName());
        validateMfo(bankRequest.getMfo());
    }

    private void validateMfo(@NonNull String mfo) throws InvalidInputDataException {
        if (isMfo(mfo)){
            return;
        }

        var message = String.format("The MFO you provided: \"%s\" of the bank has not been validated. It should contain only six digits.", mfo);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isMfo(@NonNull String mfo){
        return mfo.isEmpty() ||
                mfo.matches(MFO_TEMPLATE);
    }

    @Override
    protected Optional<Bank> findEntity(@NonNull Request request) {
        var bankRequest = (BankRequest) request;
        return repository
                .findByMfo(
                        bankRequest.getMfo()
                );
    }
}
