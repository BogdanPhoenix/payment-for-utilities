package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.ReceiptSearcher;
import org.university.payment_for_utilities.pojo.responses.receipt.BlockAccrualAmountResponse;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "blocks_accrual_amount")
public class BlockAccrualAmount extends ReceiptSearcher {
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

    @Override
    public BlockAccrualAmountResponse getResponse() {
        var responseBuilder = BlockAccrualAmountResponse.builder();
        return super
                .responseReceiptSearcherBuilder(responseBuilder)
                .debtBeginMonth(this.debtBeginMonth)
                .debtEndMonth(this.debtEndMonth)
                .fine(this.fine)
                .lastCreditedPayment(this.lastCreditedPayment)
                .amountDue(this.amountDue)
                .build();
    }
}
