package org.university.payment_for_utilities.pojo.requests.user;

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
public class ContractEntityRequest extends Request {
    private Long registeredUser;
    private Long companyTariff;
    private String numContract;

    @Override
    public boolean isEmpty() {
        return registeredUser.equals(Response.EMPTY_PARENT_ENTITY) ||
                companyTariff.equals(Response.EMPTY_PARENT_ENTITY) ||
                numContract.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull ContractEntityRequest empty() {
        return ContractEntityRequest
                .builder()
                .registeredUser(Response.EMPTY_PARENT_ENTITY)
                .companyTariff(Response.EMPTY_PARENT_ENTITY)
                .numContract("")
                .build();
    }
}
