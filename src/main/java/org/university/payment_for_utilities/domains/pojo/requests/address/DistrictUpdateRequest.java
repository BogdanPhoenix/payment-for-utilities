package org.university.payment_for_utilities.domains.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.UpdateRequest;

@Data
@Builder
public class DistrictUpdateRequest implements UpdateRequest {
    private Request oldValue;
    private Request newValue;
}
