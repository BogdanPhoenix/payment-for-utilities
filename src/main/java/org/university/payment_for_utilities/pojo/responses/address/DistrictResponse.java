package org.university.payment_for_utilities.pojo.responses.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DistrictResponse extends TransliterationResponse {
    @Contract(" -> new")
    public static @NonNull DistrictResponse empty() {
        return TransliterationResponse
                .initEmpty(builder())
                .build();
    }
}
