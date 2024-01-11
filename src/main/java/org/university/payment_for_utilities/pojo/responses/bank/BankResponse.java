package org.university.payment_for_utilities.pojo.responses.bank;

import lombok.Builder;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record BankResponse(
        Long id,
        String name,
        Website website,
        Edrpou edrpou,
        String mfo
) implements Response {

}
