package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record SettlementRequest(
        TypeSettlement type,
        String zipCode,
        SettlementName name
) implements Request {
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
                .type(TypeSettlement.empty())
                .zipCode("")
                .name(SettlementName.empty())
                .build();
    }
}
