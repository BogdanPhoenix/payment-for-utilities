package org.university.payment_for_utilities.pojo.requests.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankPhoneNumRequest extends Request {
    private Long bank;
    private Long phoneNum;

    @Override
    public boolean isEmpty() {
        return bank.equals(Response.EMPTY_PARENT_ENTITY) ||
                phoneNum.equals(Response.EMPTY_PARENT_ENTITY);
    }

    @Contract(" -> new")
    public static @NonNull BankPhoneNumRequest empty(){
        return BankPhoneNumRequest
                .builder()
                .bank(Response.EMPTY_PARENT_ENTITY)
                .phoneNum(Response.EMPTY_PARENT_ENTITY)
                .build();
    }
}
