package org.university.payment_for_utilities.pojo.requests.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.CommonInstitutionalDataRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BankRequest extends CommonInstitutionalDataRequest {
    private String mfo;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BankRequest empty(){
        return CommonInstitutionalDataRequest
                .initEmpty(builder())
                .mfo("")
                .build();
    }
}
