package org.university.payment_for_utilities.pojo.responses.bank;

import lombok.Builder;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record BankPhoneNumResponse(
        Long id,
        Bank bank,
        PhoneNum phoneNum
) implements Response {

}
