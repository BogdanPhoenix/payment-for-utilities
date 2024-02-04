package org.university.payment_for_utilities.exceptions;

/**
 * An exception thrown when there are problems with the token update.
 * Usually used to signal a failed attempt to update a JWT token.
 */
public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String message) {
        super(message);
    }
}
