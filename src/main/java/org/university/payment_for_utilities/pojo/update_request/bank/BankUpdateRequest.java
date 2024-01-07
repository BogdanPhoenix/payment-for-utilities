package org.university.payment_for_utilities.pojo.update_request.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.interfaces.UpdateRequest;

@Data
@Builder
public class BankUpdateRequest implements UpdateRequest {
    private Request oldValue;
    private Request newValue;
}
