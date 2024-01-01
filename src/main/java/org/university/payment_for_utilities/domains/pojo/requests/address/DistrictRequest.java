package org.university.payment_for_utilities.domains.pojo.requests.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictRequest {
    private String uaName;
    private String enName;
}
