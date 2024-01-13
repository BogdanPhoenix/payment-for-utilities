package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record PhoneNumRequest(
        String number
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.number.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull PhoneNumRequest empty(){
        return PhoneNumRequest
                .builder()
                .number("")
                .build();
    }
}
