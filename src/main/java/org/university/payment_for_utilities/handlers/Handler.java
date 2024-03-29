package org.university.payment_for_utilities.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.university.payment_for_utilities.exceptions.*;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            DuplicateException.class,
            NullPointerException.class,
            EmptyRequestException.class,
            IllegalStateException.class,
            TokenRefreshException.class,
            InvalidInputDataException.class,
            InvalidAuthenticationData.class,
            NotFindEntityInDataBaseException.class
    })
    public ResponseEntity<Object> handleDuplicateException(Exception ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
