package org.university.payment_for_utilities.pojo.update_request;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

/**
 * A class that represents the structure of an entity update request that can be used to interact with the system.
 */
@Data
@Builder
public class UpdateRequest {
    private Request oldValue;
    private Request newValue;
}
