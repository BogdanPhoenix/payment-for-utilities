package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;

@Builder
public record TypeSettlementRequest(
        String uaName,
        String enName
) implements TransliterationRequest {
    @Override
    public boolean isEmpty() {
        return this.uaName.isBlank() ||
                this.enName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull TypeSettlementRequest empty(){
        return TypeSettlementRequest
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
