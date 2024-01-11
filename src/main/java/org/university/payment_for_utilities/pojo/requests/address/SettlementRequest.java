package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
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
        return this.type == null || this.type.isEmpty() ||
                this.zipCode == null || this.zipCode.isEmpty() ||
                this.name == null || this.name.isEmpty();
    }
}
