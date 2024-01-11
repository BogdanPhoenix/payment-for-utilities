package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record EdrpouRequest(
        String edrpou
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.edrpou.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull EdrpouRequest empty(){
        return EdrpouRequest
                .builder()
                .edrpou("")
                .build();
    }
}
