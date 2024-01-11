package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record AddressResidenceResponse(
        Long id,
        Settlement settlement,
        String uaNameStreet,
        String enNameStreet,
        String numHouse,
        String numEntrance,
        String numApartment
) implements Response {
}
