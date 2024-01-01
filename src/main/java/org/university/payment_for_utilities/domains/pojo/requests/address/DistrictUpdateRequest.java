package org.university.payment_for_utilities.domains.pojo.requests.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictUpdateRequest {
    private String oldUaValue;
    private String newUaValue;
    private String oldEnValue;
    private String newEnValue;
}
