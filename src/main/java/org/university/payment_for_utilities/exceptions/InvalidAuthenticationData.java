package org.university.payment_for_utilities.exceptions;

public class InvalidAuthenticationData extends RuntimeException {
    public InvalidAuthenticationData(String message) {
        super(message);
    }
}
