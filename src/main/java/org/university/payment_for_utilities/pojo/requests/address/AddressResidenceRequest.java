package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record AddressResidenceRequest(
        Settlement settlement,
        String uaNameStreet,
        String enNameStreet,
        String numHouse,
        String numEntrance,
        String numApartment
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.settlement == null || this.settlement.isEmpty() ||
                this.uaNameStreet == null || this.uaNameStreet.isEmpty() ||
                this.enNameStreet == null || this.enNameStreet.isEmpty() ||
                this.numHouse == null || this.numHouse.isEmpty();
    }
}
