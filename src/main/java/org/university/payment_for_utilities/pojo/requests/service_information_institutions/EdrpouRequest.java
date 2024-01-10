package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Data
@Builder
public class EdrpouRequest implements Request {
    private String edrpou;

    @Override
    public boolean isEmpty() {
        return this.edrpou == null || this.edrpou.isEmpty();
    }
}
