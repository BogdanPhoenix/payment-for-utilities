package org.university.payment_for_utilities.domains.abstract_class;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CounterSearcherResponse;

@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CounterSearcher extends ReceiptSearcher {
    public static final Float EMPTY_COUNTER = -1.0f;

    @Column(name = "prev_value_counter", nullable = false)
    @NonNull
    private Float prevValueCounter;

    @Column(name = "current_value_counter", nullable = false)
    @NonNull
    private Float currentValueCounter;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                prevValueCounter.equals(EMPTY_COUNTER) ||
                currentValueCounter.equals(EMPTY_COUNTER);
    }

    protected static <T extends CounterSearcherBuilder<?, ?>> T initEmpty(@NonNull T builder) {
        ReceiptSearcher
                .initEmpty(builder)
                .prevValueCounter(EMPTY_COUNTER)
                .currentValueCounter(EMPTY_COUNTER);
        return builder;
    }

    protected <T extends CounterSearcherResponse.CounterSearcherResponseBuilder<?, ?>> T responseInit(@NonNull T builder) {
        super.responseInit(builder)
                .prevValueCounter(this.prevValueCounter)
                .currentValueCounter(this.currentValueCounter);
        return builder;
    }
}
