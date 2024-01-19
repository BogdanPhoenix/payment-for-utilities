package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.user.ContractEntity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "companyTariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private transient List<ContractEntity> contractEntities;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                type.isEmpty() ||
                name.isBlank() ||
                fixedCost.equals(BigDecimal.ZERO);
    }

    @Contract(" -> new")
    public static @NonNull CompanyTariff empty(){
        var builder = builder();
        TableInfo.initEmpty(builder);

        return builder
                .company(Company.empty())
                .type(TypeOffer.empty())
                .name("")
                .fixedCost(BigDecimal.ZERO)
                .build();
    }
}
