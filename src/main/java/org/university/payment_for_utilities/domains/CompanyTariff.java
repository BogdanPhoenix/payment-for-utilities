package org.university.payment_for_utilities.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "company_tariffs")
public class CompanyTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    @NonNull
    private Company company;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NonNull
    private TypeService type;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "fixed_cost", nullable = false)
    @NonNull
    private BigDecimal fixedCost;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "companyTariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contract> contracts;
}
