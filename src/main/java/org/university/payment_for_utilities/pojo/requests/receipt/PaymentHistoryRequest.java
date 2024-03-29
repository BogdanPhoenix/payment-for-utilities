package org.university.payment_for_utilities.pojo.requests.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.CounterSearcherRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentHistoryRequest extends CounterSearcherRequest {
    private String finalPaymentAmount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                finalPaymentAmount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull PaymentHistoryRequest empty() {
        return CounterSearcherRequest
                .initEmpty(builder())
                .finalPaymentAmount("")
                .build();
    }
}
