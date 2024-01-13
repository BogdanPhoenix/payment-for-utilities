package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;

@Builder
public record UnitMeasurementRequest(
        String uaName,
        String enName
) implements TransliterationRequest {
    @Override
    public boolean isEmpty() {
        return this.uaName.isBlank() ||
                this.enName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull UnitMeasurementRequest empty(){
        return UnitMeasurementRequest
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
