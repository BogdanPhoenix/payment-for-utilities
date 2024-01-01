package org.university.payment_for_utilities.domains.pojo.responses.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OblastResponse {
    private Long id;
    private String uaName;
    private String enName;
}
