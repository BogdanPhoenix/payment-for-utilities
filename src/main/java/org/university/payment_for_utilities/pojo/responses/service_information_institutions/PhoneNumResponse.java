package org.university.payment_for_utilities.pojo.responses.service_information_institutions;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PhoneNumResponse extends Response {
    private String number;

    @Override
    public boolean isEmpty() {
        return number.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull PhoneNumResponse empty() {
        return Response
                .initEmpty(builder())
                .number("")
                .build();
    }
}
