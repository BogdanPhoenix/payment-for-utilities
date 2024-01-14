package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankPhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
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
    protected void updateEntity(@NonNull BankPhoneNum entity, @NonNull Request request) {
        var newValue = (BankPhoneNumRequest) request;

        if(!newValue.bank().isEmpty()){
            entity.setBank(newValue.bank());
        }
        if(!newValue.phoneNum().isEmpty()){
            entity.setPhoneNum(newValue.phoneNum());
        }
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
