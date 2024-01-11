package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record SettlementResponse(
        Long id,
        TypeSettlement type,
        String zipCode,
        SettlementName name
) implements Response {

}
