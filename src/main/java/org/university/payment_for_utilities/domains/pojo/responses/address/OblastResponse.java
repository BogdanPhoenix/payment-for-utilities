package org.university.payment_for_utilities.domains.pojo.responses.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.pojo.responses.address.interfaces.Response;

@Data
@Builder
public class OblastResponse implements Response {
    private Long id;
    private String uaName;
    private String enName;
}
