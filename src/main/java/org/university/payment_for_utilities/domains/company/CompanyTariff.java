package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;

import java.math.BigDecimal;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "company_tariffs",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_company", "en_name"})
)
public class CompanyTariff extends TransliterationProperty {
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    @NonNull
    private Company company;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NonNull
    private TypeOffer type;

    @Column(name = "fixed_cost", nullable = false)
    @NonNull
    private BigDecimal fixedCost;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "companyTariff", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<ContractEntity> contractEntities;

    @Override
    public CompanyTariffResponse getResponse() {
        var responseBuilder = CompanyTariffResponse.builder();
        return super
                .responseTransliterationPropertyBuilder(responseBuilder)
                .company(this.company.getResponse())
                .type(this.type.getResponse())
                .fixedCost(this.fixedCost)
                .build();
    }
}
