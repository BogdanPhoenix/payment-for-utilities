package org.university.payment_for_utilities.domains.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.pojo.requests.address.interfaces.Request;

@Data
@Builder
public class OblastRequest implements Request {
    private String uaName;
    private String enName;
}
