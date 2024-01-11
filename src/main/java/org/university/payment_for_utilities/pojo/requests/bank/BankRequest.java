package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record BankRequest(
        String name,
        Website webSite,
        Edrpou edrpou,
        String mfo
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.name == null || this.name.isEmpty() ||
                this.webSite == null || webSite.isEmpty() ||
                this.edrpou == null || this.edrpou.isEmpty() ||
                this.mfo == null || this.mfo.isEmpty();
    }
}
