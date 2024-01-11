package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.TransliterationRequest;

@Builder
public record OblastRequest(
        String uaName,
        String enName
) implements TransliterationRequest {
    @Override
    public boolean isEmpty() {
        return this.uaName.isEmpty() ||
                this.enName.isEmpty();
    }
}
