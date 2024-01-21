package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "payment_history")
public class PaymentHistory extends TableInfo {
    public static final Integer EMPTY_COUNTER = -1;

    @ManyToOne
    @JoinColumn(name = "id_receipt", nullable = false, unique = true)
    @NonNull
    private Receipt receipt;

    @Column(name = "prev_value_counter", nullable = false)
    @NonNull
    private Integer prevValueCounter;

    @Column(name = "current_value_counter", nullable = false)
    @NonNull
    private Integer currentValueCounter;

    @Column(name = "final_payment_amount", nullable = false)
    @NonNull
    private BigDecimal finalPaymentAmount;

    @Override
    public boolean isEmpty() {
        return receipt.isEmpty() ||
                prevValueCounter.equals(EMPTY_COUNTER) ||
                currentValueCounter.equals(EMPTY_COUNTER) ||
                finalPaymentAmount.equals(BigDecimal.ZERO);
    }

    @Contract(" -> new")
    public static @NonNull PaymentHistory empty() {
        var builder = builder();
        TableInfo.initEmpty(builder);

        return builder
                .receipt(Receipt.empty())
                .prevValueCounter(EMPTY_COUNTER)
                .currentValueCounter(EMPTY_COUNTER)
                .finalPaymentAmount(BigDecimal.ZERO)
                .build();
    }
}
