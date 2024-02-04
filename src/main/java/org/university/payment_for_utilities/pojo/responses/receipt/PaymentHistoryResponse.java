package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CounterSearcherResponse;

import java.math.BigDecimal;

import static org.university.payment_for_utilities.services.implementations.tools.FinanceTools.EMPTY_BIG_DECIMAL;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentHistoryResponse extends CounterSearcherResponse {
    private BigDecimal finalPaymentAmount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                finalPaymentAmount.equals(EMPTY_BIG_DECIMAL);
    }

    @Contract(" -> new")
    public static @NonNull PaymentHistoryResponse empty() {
        return CounterSearcherResponse
                .initEmpty(builder())
                .finalPaymentAmount(EMPTY_BIG_DECIMAL)
                .build();
    }
}
