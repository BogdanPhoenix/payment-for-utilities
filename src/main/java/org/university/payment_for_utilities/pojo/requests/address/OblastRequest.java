package org.university.payment_for_utilities.pojo.requests.address;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.TransliterationRequest;

@Data
@Builder
public class OblastRequest implements TransliterationRequest {
    private String uaName;
    private String enName;

    @Override
    public boolean isEmpty() {
        return this.uaName.isEmpty() ||
                this.enName.isEmpty();
    }
}
