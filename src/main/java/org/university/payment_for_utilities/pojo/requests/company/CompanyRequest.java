package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.CommonInstitutionalDataRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyRequest extends CommonInstitutionalDataRequest {
    private Long address;
    private String currentAccount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                address.equals(Response.EMPTY_PARENT_ENTITY) ||
                currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyRequest empty(){
        return CommonInstitutionalDataRequest
                .initEmpty(builder())
                .address(Response.EMPTY_PARENT_ENTITY)
                .currentAccount("")
                .build();
    }
}
