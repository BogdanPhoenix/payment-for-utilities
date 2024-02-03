package org.university.payment_for_utilities.services.implementations.bank;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.bank.BankPhoneNumRepository;
import org.university.payment_for_utilities.repositories.bank.BankRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.PhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.WorkingWithPhoneNumAbstract;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.PhoneNumService;

import java.util.Optional;

@Service
public class BankPhoneNumServiceImpl extends WorkingWithPhoneNumAbstract<BankPhoneNum, BankPhoneNumRepository> implements BankPhoneNumService {
    private final PhoneNumService phoneNumService;
    private final BankRepository bankRepository;

    @Autowired
    public BankPhoneNumServiceImpl(
            BankPhoneNumRepository repository,
            PhoneNumService phoneNumService,
            PhoneNumRepository phoneNumRepository,
            BankRepository bankRepository
    ) {
        super(repository, "Bank phone nums", phoneNumRepository);
        this.phoneNumService = phoneNumService;
        this.bankRepository = bankRepository;
    }

    @Override
    protected BankPhoneNum createEntity(Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        var bank = getBank(bankPhoneNumRequest.getBank().getId());
        var phoneNum = getPhoneNum(bankPhoneNumRequest.getPhoneNum().getId());

        return BankPhoneNum
                .builder()
                .bank(bank)
                .phoneNum(phoneNum)
                .build();
    }

    @Override
    protected void deactivatedChildren(@NonNull BankPhoneNum entity) {
        deactivateChild(entity.getPhoneNum(), phoneNumService);
    }

    @Override
    protected void updateEntity(@NonNull BankPhoneNum entity, @NonNull Request request) {
        var newValue = (BankPhoneNumRequest) request;

        if(!newValue.getBank().isEmpty()){
            var bank = getBank(newValue.getBank().getId());
            entity.setBank(bank);
        }
        if(!newValue.getPhoneNum().isEmpty()){
            var phoneNum = getPhoneNum(newValue.getPhoneNum().getId());
            entity.setPhoneNum(phoneNum);
        }
    }

    @Override
    protected Optional<BankPhoneNum> findEntity(@NonNull Request request) {
        var bankPhoneNumRequest = (BankPhoneNumRequest) request;
        var bank = getBank(bankPhoneNumRequest.getBank().getId());
        var phoneNum = getPhoneNum(bankPhoneNumRequest.getPhoneNum().getId());

        return repository.findByBankAndPhoneNum(bank, phoneNum);
    }

    private @NonNull Bank getBank(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(bankRepository, id);
    }
}
