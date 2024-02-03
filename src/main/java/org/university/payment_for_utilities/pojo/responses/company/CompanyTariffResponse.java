package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

import java.math.BigDecimal;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.EMPTY_BIG_DECIMAL;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyTariffResponse extends Response {
    private CompanyResponse company;
    private TypeOfferResponse type;
    private String name;
    private BigDecimal fixedCost;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                type.isEmpty() ||
                name.isBlank() ||
                fixedCost.equals(EMPTY_BIG_DECIMAL);
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariffResponse empty(){
        return Response
                .initEmpty(builder())
                .company(CompanyResponse.empty())
                .type(TypeOfferResponse.empty())
                .name("")
                .fixedCost(EMPTY_BIG_DECIMAL)
                .build();
    }
}
