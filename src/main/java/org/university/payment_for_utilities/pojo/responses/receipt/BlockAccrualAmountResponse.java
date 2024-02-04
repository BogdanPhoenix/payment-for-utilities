package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.ReceiptSearcherResponse;

import java.math.BigDecimal;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.EMPTY_BIG_DECIMAL;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockAccrualAmountResponse extends ReceiptSearcherResponse {
    private BigDecimal debtBeginMonth;
    private BigDecimal debtEndMonth;
    private BigDecimal fine;
    private BigDecimal lastCreditedPayment;
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

    @Contract(" -> new")
    public static @NonNull BlockAccrualAmountResponse empty() {
        return ReceiptSearcherResponse
                .initEmpty(builder())
                .debtBeginMonth(EMPTY_BIG_DECIMAL)
                .debtEndMonth(EMPTY_BIG_DECIMAL)
                .fine(EMPTY_BIG_DECIMAL)
                .lastCreditedPayment(EMPTY_BIG_DECIMAL)
                .amountDue(EMPTY_BIG_DECIMAL)
                .build();
    }
}
