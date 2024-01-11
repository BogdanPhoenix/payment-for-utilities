package org.university.payment_for_utilities.pojo.responses.service_information_institutions;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record WebsiteResponse(
        Long id,
        String website
) implements Response {

}
