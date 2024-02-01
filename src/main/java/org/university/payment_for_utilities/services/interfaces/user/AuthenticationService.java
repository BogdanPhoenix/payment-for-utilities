package org.university.payment_for_utilities.services.interfaces.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.user.AuthenticationRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse registration(@NonNull RegisteredUserRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException;
    AuthenticationResponse authenticate(@NonNull AuthenticationRequest request) throws BadCredentialsException;
    void refreshToken(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws IOException;
}
