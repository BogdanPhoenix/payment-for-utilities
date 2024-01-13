package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record BankPhoneNumRequest(
        Bank bank,
        PhoneNum phoneNum
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.bank.isEmpty() ||
                this.phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull BankPhoneNumRequest empty(){
        return BankPhoneNumRequest
                .builder()
                .bank(Bank.empty())
                .phoneNum(PhoneNum.empty())
                .build();
    }
}
