package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankPhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.bank.BankPhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.PhoneNumServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

import java.util.Optional;

@Slf4j
@Service
public class BankPhoneNumServiceImpl extends PhoneNumServiceAbstract<BankPhoneNum, BankPhoneNumRepository> implements BankPhoneNumService {
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
        validatePhoneNum(bankPhoneNumRequest.phoneNum());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (BankPhoneNumRequest) updateRequest.getOldValue();
        var newValue = (BankPhoneNumRequest) updateRequest.getNewValue();

        validatePhoneNum(oldValue.phoneNum());
        validatePhoneNum(newValue.phoneNum());
    }

    @Override
    protected Optional<BankPhoneNum> findOldEntity(@NonNull Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        return repository
                .findByPhoneNum(
                        bankPhoneNumRequest.phoneNum()
                );
    }
}
