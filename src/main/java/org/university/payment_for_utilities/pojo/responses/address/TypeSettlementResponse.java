package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;

@Builder
public record TypeSettlementResponse(
        Long id,
        String uaName,
        String enName
) implements TransliterationResponse {

}
