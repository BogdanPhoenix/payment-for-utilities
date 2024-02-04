package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CounterSearcherResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockMeterReadingResponse extends CounterSearcherResponse {
    @Contract(" -> new")
    public static @NonNull BlockMeterReadingResponse empty() {
        return CounterSearcherResponse
                .initEmpty(builder())
                .build();
    }
}
