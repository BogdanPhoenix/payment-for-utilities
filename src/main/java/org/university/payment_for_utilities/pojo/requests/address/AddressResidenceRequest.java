package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;

@Data
@Builder
public class AddressResidenceRequest implements Request {
    private Settlement settlement;
    private String uaNameStreet;
    private String enNameStreet;
    private String numHouse;
    private String numEntrance;
    private String numApartment;

    @Override
    public boolean isEmpty() {
        return this.settlement == null || this.settlement.isEmpty() ||
                this.uaNameStreet == null || this.uaNameStreet.isEmpty() ||
                this.enNameStreet == null || this.enNameStreet.isEmpty() ||
                this.numHouse == null || this.numHouse.isEmpty();
    }
}
