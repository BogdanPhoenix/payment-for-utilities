package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;

@Data
@Builder
public class SettlementRequest implements Request {
    private TypeSettlement type;
    private String zipCode;
    private SettlementName name;

    @Override
    public boolean isEmpty() {
        return type == null ||
                zipCode == null ||
                name == null;
    }
}
