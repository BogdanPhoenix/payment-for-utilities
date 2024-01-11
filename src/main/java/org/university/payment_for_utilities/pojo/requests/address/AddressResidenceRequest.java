package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record AddressResidenceRequest(
        Settlement settlement,
        String uaNameStreet,
        String enNameStreet,
        String numHouse,
        String numEntrance,
        String numApartment
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.settlement.isEmpty() ||
                this.uaNameStreet.isBlank() ||
                this.enNameStreet.isBlank() ||
                this.numHouse.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull AddressResidenceRequest empty(){
        return AddressResidenceRequest
                .builder()
                .settlement(Settlement.empty())
                .uaNameStreet("")
                .enNameStreet("")
                .numHouse("")
                .build();
    }
}
