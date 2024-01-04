package org.university.payment_for_utilities.pojo.update_request.address.interfaces;

import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;

/**
 * An interface representing the request structure for updating an entity that can be used to interact with the system.
 */
public interface UpdateRequest {
    /**
     * Retrieves the old value from the request.
     *
     * @return The old value associated with the request.
     */
    Request getOldValue();

    /**
     * Retrieves the new value from the request.
     *
     * @return The new value associated with the request.
     */
    Request getNewValue();
}
