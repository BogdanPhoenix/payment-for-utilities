package org.university.payment_for_utilities.pojo.requests.service_information_institutions;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record WebsiteRequest(
        String website
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.website.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull WebsiteRequest empty(){
        return WebsiteRequest
                .builder()
                .website("")
                .build();
    }
}
