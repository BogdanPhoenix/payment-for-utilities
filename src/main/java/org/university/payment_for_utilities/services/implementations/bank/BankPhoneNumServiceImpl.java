package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankPhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.bank.BankPhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

import java.util.Optional;

@Slf4j
@Service
public class BankPhoneNumServiceImpl extends CrudServiceAbstract<BankPhoneNum, BankPhoneNumRepository> implements BankPhoneNumService {
    private static final String PHONE_NUM_TEMPLATE = "^380\\d{9}$";
    public BankPhoneNumServiceImpl(BankPhoneNumRepository repository) {
        super(repository, "Bank phone nums");
    }

    @Override
    protected BankPhoneNum createEntity(Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        return BankPhoneNum
                .builder()
                .bank(bankPhoneNumRequest.getBank())
                .phoneNum(bankPhoneNumRequest.getPhoneNum())
                .build();
    }

    @Override
    protected BankPhoneNum createEntity(Response response) {
        var bankPhoneNumResponse = (BankPhoneNumResponse) response;
        return BankPhoneNum
                .builder()
                .id(bankPhoneNumResponse.getId())
                .bank(bankPhoneNumResponse.getBank())
                .phoneNum(bankPhoneNumResponse.getPhoneNum())
                .build();
    }

    @Override
    protected Response createResponse(@NonNull BankPhoneNum entity) {
        return BankPhoneNumResponse
                .builder()
                .id(entity.getId())
                .bank(entity.getBank())
                .phoneNum(entity.getPhoneNum())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull BankPhoneNum entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (BankPhoneNumRequest) updateRequest.getOldValue();
        var newValue = (BankPhoneNumRequest) updateRequest.getNewValue();

        entity.setBank(
                updateAttribute(
                        oldValue.getBank(),
                        newValue.getBank()
                )
        );
        entity.setPhoneNum(
                updateAttribute(
                        oldValue.getPhoneNum(),
                        newValue.getPhoneNum()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;

        validateBank(bankPhoneNumRequest.getBank());
        validatePhoneNum(bankPhoneNumRequest.getPhoneNum());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (BankPhoneNumRequest) updateRequest.getOldValue();
        var newValue = (BankPhoneNumRequest) updateRequest.getNewValue();

        validatePhoneNum(oldValue.getPhoneNum());
        validatePhoneNum(newValue.getPhoneNum());
    }

    private void validateBank(Bank bank) throws InvalidInputDataException{
        if(isValidBank(bank)){
            return;
        }

        var message = String.format("The bank entity you provided has not been validated: \"%s\". The bank entity cannot be null or empty.", bank);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isValidBank(Bank bank){
        return bank != null && !bank.isEmpty();
    }

    private void validatePhoneNum(String phoneNum) throws InvalidInputDataException{
        if(isValidPhoneNum(phoneNum)){
            return;
        }

        var message = String.format("The mobile phone number you provided was not validated: \"%s\". The phone number must consist of twelve digits and start with \"380\".", phoneNum);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    @Contract(pure = true)
    private boolean isValidPhoneNum(@NonNull String phoneNum){
        return phoneNum
                .matches(PHONE_NUM_TEMPLATE);
    }

    @Override
    protected Optional<BankPhoneNum> findOldEntity(@NonNull Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        return repository
                .findByPhoneNum(
                        bankPhoneNumRequest.getPhoneNum()
                );
    }
}
