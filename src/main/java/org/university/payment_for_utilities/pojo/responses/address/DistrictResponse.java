package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;

@Builder
public record DistrictResponse(
        Long id,
        String uaName,
        String enName
) implements TransliterationResponse {

}
