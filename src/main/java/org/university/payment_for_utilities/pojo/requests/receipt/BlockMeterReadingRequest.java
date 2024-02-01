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
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockMeterReadingRequest extends CounterSearcherRequest {
    @Contract(" -> new")
    public static @NonNull BlockMeterReadingRequest empty() {
        return CounterSearcherRequest
                .initEmpty(builder())
                .build();
    }
}
