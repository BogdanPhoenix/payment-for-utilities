package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.Response;

@Data
@Builder
public class SettlementResponse implements Response {
    private Long id;
    private TypeSettlement type;
    private String zipCode;
    private SettlementName name;
}
