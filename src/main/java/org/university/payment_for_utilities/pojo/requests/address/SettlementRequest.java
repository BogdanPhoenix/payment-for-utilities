package org.university.payment_for_utilities.pojo.requests.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.address.SettlementNameResponse;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SettlementRequest extends Request {
    private TypeSettlementResponse type;
    private String zipCode;
    private SettlementNameResponse name;

    @Override
    public boolean isEmpty() {
        return this.type.isEmpty() ||
                this.zipCode.isBlank() ||
                this.name.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull SettlementRequest empty(){
        return SettlementRequest
                .builder()
                .type(TypeSettlementResponse.empty())
                .zipCode("")
                .name(SettlementNameResponse.empty())
                .build();
    }
}
