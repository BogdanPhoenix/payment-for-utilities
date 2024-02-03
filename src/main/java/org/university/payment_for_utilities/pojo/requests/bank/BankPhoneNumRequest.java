package org.university.payment_for_utilities.pojo.requests.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankPhoneNumRequest extends Request {
    private BankResponse bank;
    private PhoneNumResponse phoneNum;

    @Override
    public boolean isEmpty() {
        return this.bank.isEmpty() ||
                this.phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull BankPhoneNumRequest empty(){
        return BankPhoneNumRequest
                .builder()
                .bank(BankResponse.empty())
                .phoneNum(PhoneNumResponse.empty())
                .build();
    }
}
