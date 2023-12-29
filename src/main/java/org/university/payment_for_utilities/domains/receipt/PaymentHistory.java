package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.receipt.Receipt;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_receipt", nullable = false, unique = true)
    @NonNull
    private Receipt receipt;

    @Column(name = "date_time_payment", nullable = false)
    @NonNull
    private Timestamp dateTimePayment;

    @Column(name = "prev_value_counter", nullable = false)
    @NonNull
    private Short prevValueCounter;

    @Column(name = "current_value_counter", nullable = false)
    @NonNull
    private Short currentValueCounter;

    @Column(name = "final_payment_amount", nullable = false)
    @NonNull
    private BigDecimal finalPaymentAmount;

    @Column(name = "current_data")
    private boolean currentData;
}
