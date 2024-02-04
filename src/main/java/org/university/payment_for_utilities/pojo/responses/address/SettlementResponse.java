package org.university.payment_for_utilities.pojo.responses.address;

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
public class SettlementResponse extends Response {
    private TypeSettlementResponse type;
    private String zipCode;
    private SettlementNameResponse name;

    @Override
    public boolean isEmpty() {
        return type.isEmpty() ||
                zipCode.isBlank() ||
                name.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull SettlementResponse empty() {
        return Response
                .initEmpty(builder())
                .type(TypeSettlementResponse.empty())
                .zipCode("")
                .name(SettlementNameResponse.empty())
                .build();
    }
}
