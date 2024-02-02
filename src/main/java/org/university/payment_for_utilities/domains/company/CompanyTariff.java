package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;

import java.math.BigDecimal;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.EMPTY_BIG_DECIMAL;

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
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_company", "name"})
)
public class CompanyTariff extends TableInfo {
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    @NonNull
    private Company company;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NonNull
    private TypeOffer type;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "fixed_cost", nullable = false)
    @NonNull
    private BigDecimal fixedCost;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "companyTariff", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<ContractEntity> contractEntities;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                type.isEmpty() ||
                name.isBlank() ||
                fixedCost.equals(EMPTY_BIG_DECIMAL);
    }

    @Override
    public Response getResponse() {
        var responseBuilder = CompanyTariffResponse.builder();
        return super
                .responseInit(responseBuilder)
                .company(this.company)
                .type(this.type)
                .name(this.name)
                .fixedCost(this.fixedCost)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariff empty(){
        return TableInfo
                .initEmpty(builder())
                .company(Company.empty())
                .type(TypeOffer.empty())
                .name("")
                .fixedCost(EMPTY_BIG_DECIMAL)
                .build();
    }
}
