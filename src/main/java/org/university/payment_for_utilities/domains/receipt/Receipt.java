package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.domains.bank.Bank;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "receipts",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_contract", "id_bank", "bill_month_and_year", "date_time_create"})
)
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_contract", nullable = false)
    @NonNull
    private ContractEntity contractEntity;

    @ManyToOne
    @JoinColumn(name = "id_bank", nullable = false)
    @NonNull
    private Bank bank;

    @Column(name = "bill_month_and_year", nullable = false)
    @NonNull
    private Date billMonthAndYear;

    @Column(name = "date_time_create", nullable = false)
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentHistory> paymentHistories;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockAccrualAmount> blockAccrualAmounts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockMeterReading> blockMeterReadings;
}
