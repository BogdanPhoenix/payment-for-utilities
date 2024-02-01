package org.university.payment_for_utilities.pojo.requests.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;

import static org.university.payment_for_utilities.domains.abstract_class.CounterSearcher.EMPTY_COUNTER;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CounterSearcherRequest extends ReceiptSearcherRequest {
    private Float prevValueCounter;
    private Float currentValueCounter;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                prevValueCounter.equals(EMPTY_COUNTER) ||
                currentValueCounter.equals(EMPTY_COUNTER);
    }

    @Contract("_ -> param1")
    protected static <T extends CounterSearcherRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        ReceiptSearcherRequest
                .initEmpty(builder)
                .prevValueCounter(EMPTY_COUNTER)
                .currentValueCounter(EMPTY_COUNTER);
        return builder;
    }
}
