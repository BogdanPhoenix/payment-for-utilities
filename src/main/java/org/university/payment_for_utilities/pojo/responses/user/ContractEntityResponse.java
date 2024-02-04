package org.university.payment_for_utilities.pojo.responses.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractEntityResponse extends Response {
    private UserResponse registeredUser;
    private CompanyTariffResponse companyTariff;
    private String numContract;

    @Override
    public boolean isEmpty() {
        return registeredUser.isEmpty() ||
                companyTariff.isEmpty() ||
                numContract.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull ContractEntityResponse empty() {
        return Response
                .initEmpty(builder())
                .registeredUser(UserResponse.empty())
                .companyTariff(CompanyTariffResponse.empty())
                .numContract("")
                .build();
    }
}
