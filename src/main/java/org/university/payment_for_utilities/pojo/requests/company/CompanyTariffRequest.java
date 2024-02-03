package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyTariffRequest extends Request {
    private CompanyResponse company;
    private TypeOfferResponse type;
    private String name;
    private String fixedCost;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                type.isEmpty() ||
                name.isBlank() ||
                fixedCost.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariffRequest empty() {
        return CompanyTariffRequest
                .builder()
                .company(CompanyResponse.empty())
                .type(TypeOfferResponse.empty())
                .name("")
                .fixedCost("")
                .build();
    }
}
