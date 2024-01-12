package org.university.payment_for_utilities.pojo.responses.service_information_institutions;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;

@Builder
public record UnitMeasurementResponse(
        Long id,
        String uaName,
        String enName
) implements TransliterationResponse {
}
