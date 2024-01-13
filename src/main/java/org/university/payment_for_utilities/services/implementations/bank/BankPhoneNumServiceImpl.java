package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
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
import org.university.payment_for_utilities.services.implementations.WorkingWithPhoneNumAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

import java.util.Optional;

@Service
public class BankPhoneNumServiceImpl extends WorkingWithPhoneNumAbstract<BankPhoneNum, BankPhoneNumRepository> implements BankPhoneNumService {
    public BankPhoneNumServiceImpl(BankPhoneNumRepository repository) {
        super(repository, "Bank phone nums");
    }

    @Override
    protected BankPhoneNum createEntity(Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        return BankPhoneNum
                .builder()
                .bank(bankPhoneNumRequest.bank())
                .phoneNum(bankPhoneNumRequest.phoneNum())
                .currentData(true)
                .build();
    }

    @Override
    protected BankPhoneNum createEntity(Response response) {
        var bankPhoneNumResponse = (BankPhoneNumResponse) response;
        return BankPhoneNum
                .builder()
                .id(bankPhoneNumResponse.id())
                .bank(bankPhoneNumResponse.bank())
                .phoneNum(bankPhoneNumResponse.phoneNum())
                .currentData(true)
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
                        oldValue.bank(),
                        newValue.bank()
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
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;

        validateBank(bankPhoneNumRequest.bank());
        validatePhone(bankPhoneNumRequest.phoneNum());
    }

    private void validateBank(Bank bank) throws InvalidInputDataException {
        if (isValidBank(bank)) {
            return;
        }

        var message = String.format("The bank entity you provided has not been validated: \"%s\". The bank entity cannot be null or empty.", bank);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidBank(@NonNull Bank bank) {
        return !bank.isEmpty();
    }

    @Override
    protected Optional<BankPhoneNum> findOldEntity(@NonNull Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        return repository
                .findByBankAndPhoneNum(
                        bankPhoneNumRequest.bank(),
                        bankPhoneNumRequest.phoneNum()
                );
    }
}
