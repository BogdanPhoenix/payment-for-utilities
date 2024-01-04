package org.university.payment_for_utilities.domains.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.domains.receipt.Receipt;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_registered_user", nullable = false)
    @NonNull
    private RegisteredUser registeredUser;

    @ManyToOne
    @JoinColumn(name = "id_company_tariff", nullable = false)
    @NonNull
    private CompanyTariff companyTariff;

    @Column(name = "num_contract", length = 11, nullable = false, unique = true)
    @NonNull
    private String numContract;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Receipt> receipts;
}
