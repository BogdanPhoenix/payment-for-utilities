package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record EdrpouRequest(
        String edrpou
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.edrpou == null || this.edrpou.isEmpty();
    }
}
