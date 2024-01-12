package org.university.payment_for_utilities.pojo.responses.company;

import lombok.Builder;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record TypeOfferResponse(
        Long id,
        UnitMeasurement unitMeasurement,
        String uaName,
        String enName
) implements Response {
}
