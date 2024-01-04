package org.university.payment_for_utilities.pojo.requests.address.interfaces;

/**
 * An interface representing the query structure that can be used to interact with the system.
 */
public interface Request {
    /**
     * Checks if the request is empty.
     *
     * @return true if the request does not contain at least one empty attribute, otherwise false.
     */
    boolean isEmpty();
}
