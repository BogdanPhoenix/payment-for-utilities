package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyTariffRequest extends TransliterationRequest {
    private Long company;
    private Long type;
    private String fixedCost;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                company.equals(Response.EMPTY_PARENT_ENTITY) ||
                type.equals(Response.EMPTY_PARENT_ENTITY) ||
                fixedCost.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariffRequest empty() {
        return TransliterationRequest
                .initEmpty(builder())
                .company(Response.EMPTY_PARENT_ENTITY)
                .type(Response.EMPTY_PARENT_ENTITY)
                .fixedCost("")
                .build();
    }
}
