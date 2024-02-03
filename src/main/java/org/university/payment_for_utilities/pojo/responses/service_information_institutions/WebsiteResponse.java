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
public class WebsiteResponse extends Response {
    private String value;

    @Override
    public boolean isEmpty() {
        return value.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull WebsiteResponse empty(){
        return Response.
                initEmpty(builder())
                .value("")
                .build();
    }
}
