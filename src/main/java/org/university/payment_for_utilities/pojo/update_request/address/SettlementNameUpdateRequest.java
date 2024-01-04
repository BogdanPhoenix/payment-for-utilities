package org.university.payment_for_utilities.pojo.update_request.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;

@Data
@Builder
public class SettlementNameUpdateRequest implements UpdateRequest {
    private Request oldValue;
    private Request newValue;
}
