package org.university.payment_for_utilities.pojo.requests.company;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
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
        return this.address.isEmpty() ||
                this.edrpou.isEmpty() ||
                this.website.isEmpty() ||
                this.name.isBlank() ||
                this.currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyRequest empty(){
        return CompanyRequest
                .builder()
                .address(AddressResidence.empty())
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .currentAccount("")
                .build();
    }
}
