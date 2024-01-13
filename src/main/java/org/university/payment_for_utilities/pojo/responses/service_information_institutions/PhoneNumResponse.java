package org.university.payment_for_utilities.pojo.responses.service_information_institutions;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record PhoneNumResponse(
        Long id,
        String number
) implements Response {
}
