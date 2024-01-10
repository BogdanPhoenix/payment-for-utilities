package org.university.payment_for_utilities.pojo.responses.service_information_institutions;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Data
@Builder
public class WebsiteResponse implements Response {
    private Long id;
    private String website;
}
