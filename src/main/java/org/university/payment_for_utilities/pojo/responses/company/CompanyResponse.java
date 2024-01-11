package org.university.payment_for_utilities.pojo.responses.company;

import lombok.Builder;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record CompanyResponse(
        Long id,
        AddressResidence address,
        Edrpou edrpou,
        Website website,
        String name,
        String currentAccount
) implements Response {
}
