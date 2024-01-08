package org.university.payment_for_utilities.pojo.responses.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Data
@Builder
public class BankPhoneNumResponse implements Response {
    private Long id;
    private Bank bank;
    private String phoneNum;
}
