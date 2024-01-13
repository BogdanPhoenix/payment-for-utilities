package org.university.payment_for_utilities.pojo.requests.company;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;

@Builder
public record TypeOfferRequest(
        UnitMeasurement unitMeasurement,
        String uaName,
        String enName
) implements TransliterationRequest {
    @Override
    public boolean isEmpty() {
        return this.uaName.isBlank() ||
                this.enName.isBlank() ||
                this.unitMeasurement.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull TypeOfferRequest empty(){
        return TypeOfferRequest
                .builder()
                .unitMeasurement(UnitMeasurement.empty())
                .uaName("")
                .enName("")
                .build();
    }
}
