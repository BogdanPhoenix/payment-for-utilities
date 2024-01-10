package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Data
@Builder
public class WebsiteRequest implements Request {
    private String website;

    @Override
    public boolean isEmpty() {
        return this.website == null || this.website.isEmpty();
    }
}
