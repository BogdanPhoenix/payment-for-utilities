package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.ReceiptSearcher;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.receipt.BlockAccrualAmountResponse;

import java.math.BigDecimal;

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
    public boolean isEmpty() {
        return super.isEmpty() ||
                debtBeginMonth.equals(EMPTY_BIG_DECIMAL) ||
                debtEndMonth.equals(EMPTY_BIG_DECIMAL) ||
                fine.equals(EMPTY_BIG_DECIMAL) ||
                lastCreditedPayment.equals(EMPTY_BIG_DECIMAL) ||
                amountDue.equals(EMPTY_BIG_DECIMAL);
    }

    @Override
    public Response getResponse() {
        var responseBuilder = BlockAccrualAmountResponse.builder();
        return super
                .responseInit(responseBuilder)
                .debtBeginMonth(this.debtBeginMonth)
                .debtEndMonth(this.debtEndMonth)
                .fine(this.fine)
                .lastCreditedPayment(this.lastCreditedPayment)
                .amountDue(this.amountDue)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull BlockAccrualAmount empty() {
        return ReceiptSearcher
                .initEmpty(builder())
                .debtBeginMonth(EMPTY_BIG_DECIMAL)
                .debtEndMonth(EMPTY_BIG_DECIMAL)
                .fine(EMPTY_BIG_DECIMAL)
                .lastCreditedPayment(EMPTY_BIG_DECIMAL)
                .amountDue(EMPTY_BIG_DECIMAL)
                .build();
    }
}
