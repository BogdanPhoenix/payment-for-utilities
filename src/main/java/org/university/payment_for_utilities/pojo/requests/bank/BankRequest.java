package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record BankRequest(
        String name,
        Website website,
        Edrpou edrpou,
        String mfo
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.name.isBlank() ||
                this.website.isEmpty() ||
                this.edrpou.isEmpty() ||
                this.mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BankRequest empty(){
        return BankRequest
                .builder()
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .mfo("")
                .build();
    }
}
