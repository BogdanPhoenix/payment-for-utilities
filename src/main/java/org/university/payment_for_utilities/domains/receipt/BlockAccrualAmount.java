package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "blocks_accrual_amount")
public class BlockAccrualAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_receipt", nullable = false, unique = true)
    @NonNull
    private Receipt receipt;

    @Column(name = "debt_begin_month", nullable = false)
    @NonNull
    private BigDecimal debtBeginMonth;

    @Column(name = "debt_end_month", nullable = false)
    @NonNull
    private BigDecimal debtEndMonth;

    @Column(name = "fine", nullable = false)
    @NonNull
    private BigDecimal fine;

    @Column(name = "last_credited_payment", nullable = false)
    @NonNull
    private BigDecimal lastCreditedPayment;

    @Column(name = "amount_due", nullable = false)
    @NonNull
    private BigDecimal amountDue;

    @Column(name = "current_data")
    private boolean currentData;
}
