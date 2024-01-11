package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record WebsiteRequest(
        String website
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.website == null || this.website.isEmpty();
    }
}
