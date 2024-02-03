package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;

import java.math.BigDecimal;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.EMPTY_BIG_DECIMAL;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyTariffResponse extends TransliterationResponse {
    private CompanyResponse company;
    private TypeOfferResponse type;
    private BigDecimal fixedCost;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                company.isEmpty() ||
                type.isEmpty() ||
                fixedCost.equals(EMPTY_BIG_DECIMAL);
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariffResponse empty(){
        return TransliterationResponse
                .initEmpty(builder())
                .company(CompanyResponse.empty())
                .type(TypeOfferResponse.empty())
                .fixedCost(EMPTY_BIG_DECIMAL)
                .build();
    }
}
