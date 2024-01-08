package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.bank.BankRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankService;

import java.util.Optional;

@Slf4j
@Service
public class BankServiceImpl extends CrudServiceAbstract<Bank, BankRepository> implements BankService {
    private static final String WEBSITE_TEMPLATE = "^(http|https)://.+";
    private static final String EDRPOU_TEMPLATE = "^\\d{8}$";
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
                .webSite(bankRequest.getWebSite())
                .edrpou(bankRequest.getEdrpou())
                .mfo(bankRequest.getMfo())
                .currentData(true)
                .build();
    }

    @Override
    protected Bank createEntity(Response response) {
        var bankResponse = (BankResponse) response;
        return Bank
                .builder()
                .id(bankResponse.getId())
                .name(bankResponse.getName())
                .webSite(bankResponse.getWebSite())
                .edrpou(bankResponse.getEdrpou())
                .mfo(bankResponse.getMfo())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Bank entity) {
        return BankResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .webSite(entity.getWebSite())
                .edrpou(entity.getEdrpou())
                .mfo(entity.getMfo())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Bank entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (BankRequest) updateRequest.getOldValue();
        var newValue = (BankRequest) updateRequest.getNewValue();

        entity.setName(
                updateAttribute(
                        oldValue.getName(),
                        newValue.getName()
                )
        );
        entity.setWebSite(
                updateAttribute(
                        oldValue.getWebSite(),
                        newValue.getWebSite()
                )
        );
        entity.setEdrpou(
                updateAttribute(
                        oldValue.getEdrpou(),
                        newValue.getEdrpou()
                )
        );
        entity.setMfo(
                updateAttribute(
                        oldValue.getMfo(),
                        newValue.getMfo()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var bankRequest = (BankRequest) request;

        validateName(bankRequest.getName());
        validateWebSite(bankRequest.getWebSite());
        validateEdrpou(bankRequest.getEdrpou());
        validateMfo(bankRequest.getMfo());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (BankRequest) updateRequest.getOldValue();
        var newValue = (BankRequest) updateRequest.getNewValue();

        validateName(oldValue.getName());
        validateName(newValue.getName());

        validateWebSite(oldValue.getWebSite());
        validateWebSite(newValue.getWebSite());

        validateEdrpou(oldValue.getEdrpou());
        validateEdrpou(newValue.getEdrpou());

        validateMfo(oldValue.getMfo());
        validateMfo(newValue.getMfo());
    }

    private void validateWebSite(@NonNull String webSite) throws InvalidInputDataException {
        if (isWebSite(webSite)){
            return;
        }

        var message = String.format("The link you provided: \"%s\" to the bank's website was not validated. It must match the following template: \"%s\".", webSite, WEBSITE_TEMPLATE);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isWebSite(@NonNull String webSite){
        return webSite.isEmpty() ||
                webSite.toLowerCase()
                .matches(WEBSITE_TEMPLATE);
    }

    private void validateEdrpou(@NonNull String edrpou) throws InvalidInputDataException {
        if (isEdrpou(edrpou)){
            return;
        }

        var message = String.format("The EDRPOU: \"%s\" of the bank has not been validated. It should contain only eight digits.", edrpou);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isEdrpou(@NonNull String edrpou){
        return edrpou
                .matches(EDRPOU_TEMPLATE);
    }

    private void validateMfo(@NonNull String mfo) throws InvalidInputDataException {
        if (isMfo(mfo)){
            return;
        }

        var message = String.format("The MFO you provided: \"%s\" of the bank has not been validated. It should contain only six digits.", mfo);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isMfo(@NonNull String mfo){
        return mfo.isEmpty() ||
                mfo.matches(MFO_TEMPLATE);
    }

    @Override
    protected Optional<Bank> findOldEntity(@NonNull Request request) {
        var bankRequest = (BankRequest) request;
        return repository
                .findByEdrpou(
                        bankRequest.getEdrpou()
                );
    }
}
