package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record BankPhoneNumRequest(
        Bank bank,
        String phoneNum
) implements Request {
    @Override
    public boolean isEmpty() {
        return bank == null || bank.isEmpty() ||
                phoneNum == null || phoneNum.isEmpty();
    }
}
