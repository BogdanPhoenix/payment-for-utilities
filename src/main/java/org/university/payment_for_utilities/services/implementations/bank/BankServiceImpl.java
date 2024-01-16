package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
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
                .name(bankRequest.name())
                .website(bankRequest.website())
                .edrpou(bankRequest.edrpou())
                .mfo(bankRequest.mfo())
                .currentData(true)
                .build();
    }

    @Override
    protected Bank createEntity(Response response) {
        var bankResponse = (BankResponse) response;
        return Bank
                .builder()
                .id(bankResponse.id())
                .name(bankResponse.name())
                .website(bankResponse.website())
                .edrpou(bankResponse.edrpou())
                .mfo(bankResponse.mfo())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Bank entity) {
        return BankResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .website(entity.getWebsite())
                .edrpou(entity.getEdrpou())
                .mfo(entity.getMfo())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Bank entity, @NonNull Request request) {
        var newValue = (BankRequest) request;

        if(!newValue.name().isBlank()){
            entity.setName(newValue.name());
        }
        if(!newValue.website().isEmpty()){
            entity.setWebsite(newValue.website());
        }
        if(!newValue.edrpou().isEmpty()){
            entity.setEdrpou(newValue.edrpou());
        }
        if(!newValue.mfo().isBlank()){
            entity.setMfo(newValue.mfo());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var bankRequest = (BankRequest) request;

        validateName(bankRequest.name());
        validateMfo(bankRequest.mfo());
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
                        bankRequest.mfo()
                );
    }
}
