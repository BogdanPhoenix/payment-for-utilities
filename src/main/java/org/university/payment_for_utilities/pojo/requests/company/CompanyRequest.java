package org.university.payment_for_utilities.pojo.requests.company;

import lombok.Builder;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record CompanyRequest(
        AddressResidence address,
        Edrpou edrpou,
        Website website,
        String name,
        String currentAccount

) implements Request {
    @Override
    public boolean isEmpty() {
        return this.address == null || this.address.isEmpty() ||
                this.edrpou == null || this.edrpou.isEmpty() ||
                this.website == null || this.website.isEmpty() ||
                this.name == null || this.name.isEmpty();
    }
}
