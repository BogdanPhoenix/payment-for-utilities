package org.university.payment_for_utilities.exceptions;

/**
 * Exception thrown to indicate that the provided authentication data is invalid.
 * This is typically used in authentication processes where the provided credentials or data
 * do not match the expected or valid format.
 */
public class InvalidAuthenticationData extends RuntimeException {
    public InvalidAuthenticationData(String message) {
        super(message);
    }
}
