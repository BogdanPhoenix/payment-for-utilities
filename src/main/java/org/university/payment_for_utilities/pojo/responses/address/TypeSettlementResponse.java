package org.university.payment_for_utilities.pojo.responses.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;

@Data
@Builder
public class TypeSettlementResponse implements TransliterationResponse {
    private Long id;
    private String uaName;
    private String enName;
}
