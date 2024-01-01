package org.university.payment_for_utilities.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message){
        super(message);
    }
}
