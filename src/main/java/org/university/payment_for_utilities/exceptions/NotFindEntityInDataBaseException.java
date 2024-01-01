package org.university.payment_for_utilities.exceptions;

import java.util.function.Supplier;

public class NotFindEntityInDataBaseException extends RuntimeException{
    public NotFindEntityInDataBaseException(String message) {
        super(message);
    }
}
