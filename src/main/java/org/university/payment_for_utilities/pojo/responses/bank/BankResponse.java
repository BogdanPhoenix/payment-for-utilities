package org.university.payment_for_utilities.pojo.responses.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CommonInstitutionalDataResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BankResponse extends CommonInstitutionalDataResponse {
    private String mfo;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BankResponse empty() {
        return CommonInstitutionalDataResponse
                .initEmpty(builder())
                .mfo("")
                .build();
    }
}
