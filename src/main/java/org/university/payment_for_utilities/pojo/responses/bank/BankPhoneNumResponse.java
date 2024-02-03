package org.university.payment_for_utilities.pojo.responses.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BankPhoneNumResponse extends Response {
    private BankResponse bank;
    private PhoneNumResponse phoneNum;

    @Override
    public boolean isEmpty() {
        return bank.isEmpty() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull BankPhoneNumResponse empty() {
        return Response
                .initEmpty(builder())
                .bank(BankResponse.empty())
                .phoneNum(PhoneNumResponse.empty())
                .build();
    }
}
