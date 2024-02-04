package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyTariffRequest extends TransliterationRequest {
    private CompanyResponse company;
    private TypeOfferResponse type;
    private String fixedCost;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                company.isEmpty() ||
                type.isEmpty() ||
                fixedCost.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariffRequest empty() {
        return TransliterationRequest
                .initEmpty(builder())
                .company(CompanyResponse.empty())
                .type(TypeOfferResponse.empty())
                .fixedCost("")
                .build();
    }
}
