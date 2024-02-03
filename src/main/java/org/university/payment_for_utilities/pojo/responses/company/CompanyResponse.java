package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CommonInstitutionalDataResponse;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyResponse extends CommonInstitutionalDataResponse {
    private AddressResidenceResponse address;
    private String currentAccount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                address.isEmpty() ||
                currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyResponse empty(){
        return CommonInstitutionalDataResponse
                .initEmpty(builder())
                .address(AddressResidenceResponse.empty())
                .currentAccount("")
                .build();
    }
}
