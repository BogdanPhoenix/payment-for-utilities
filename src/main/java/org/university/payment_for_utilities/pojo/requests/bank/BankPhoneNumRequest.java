package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Data
@Builder
public class BankPhoneNumRequest implements Request {
    private Bank bank;
    private String phoneNum;

    @Override
    public boolean isEmpty() {
        return bank == null || bank.isEmpty() ||
                phoneNum == null || phoneNum.isEmpty();
    }
}
