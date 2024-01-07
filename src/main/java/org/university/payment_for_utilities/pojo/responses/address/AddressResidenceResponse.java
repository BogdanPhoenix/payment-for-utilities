package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.Response;

@Data
@Builder
public class AddressResidenceResponse implements Response {
    private Long id;
    private Settlement settlement;
    private String uaNameStreet;
    private String enNameStreet;
    private String numHouse;
    private String numEntrance;
    private String numApartment;
}
