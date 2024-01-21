package org.university.payment_for_utilities.pojo.requests.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;

import static org.university.payment_for_utilities.domains.receipt.PaymentHistory.EMPTY_COUNTER;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentHistoryRequest extends Request {
    private Receipt receipt;
    private Integer prevValueCounter;
    private Integer currentValueCounter;
    private String finalPaymentAmount;

    @Override
    public boolean isEmpty() {
        return receipt.isEmpty() ||
                prevValueCounter.equals(EMPTY_COUNTER) ||
                currentValueCounter.equals(EMPTY_COUNTER) ||
                finalPaymentAmount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull PaymentHistoryRequest empty() {
        return PaymentHistoryRequest
                .builder()
                .receipt(Receipt.empty())
                .prevValueCounter(EMPTY_COUNTER)
                .currentValueCounter(EMPTY_COUNTER)
                .finalPaymentAmount("")
                .build();
    }
}
