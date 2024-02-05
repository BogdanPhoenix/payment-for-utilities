package org.university.payment_for_utilities.pojo.requests.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SettlementRequest extends Request {
    private Long type;
    private String zipCode;
    private Long name;

    @Override
    public boolean isEmpty() {
        return type.equals(Response.EMPTY_PARENT_ENTITY) ||
                zipCode.isBlank() ||
                name.equals(Response.EMPTY_PARENT_ENTITY);
    }

    @Contract(" -> new")
    public static @NonNull SettlementRequest empty(){
        return SettlementRequest
                .builder()
                .type(Response.EMPTY_PARENT_ENTITY)
                .zipCode("")
                .name(Response.EMPTY_PARENT_ENTITY)
                .build();
    }
}
