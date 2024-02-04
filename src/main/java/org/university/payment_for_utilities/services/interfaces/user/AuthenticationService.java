package org.university.payment_for_utilities.services.interfaces.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.user.AuthenticationRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;

@Transactional
public interface AuthenticationService {
    /**
     * Handles the registration process for a new user based on the provided registration request.
     * Validates the input, adds the user to the system, and creates an authentication response.
     *
     * @param request the registration request containing user information.
     * @return an AuthenticationResponse containing details for the registered user.
     * @throws EmptyRequestException     if the registration request is empty or contains null values.
     * @throws InvalidInputDataException if the input data in the registration request is invalid.
     * @throws DuplicateException        if the user already exists in the system.
     */
    AuthenticationResponse registration(@NonNull RegisteredUserRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException;

    /**
     * Authenticates a user based on the provided authentication request.
     * Utilizes the authentication manager to validate the provided credentials,
     * and generates an AuthenticationResponse with necessary details.
     *
     * @param request the authentication request containing username and password.
     * @return an AuthenticationResponse containing details for the authenticated user.
     * @throws BadCredentialsException if the provided credentials are invalid.
     */
    AuthenticationResponse authenticate(@NonNull AuthenticationRequest request) throws BadCredentialsException;

    /**
     * Refreshes the authentication token for a user based on the provided HttpServletRequest and HttpServletResponse.
     *
     * @param request  the HttpServletRequest containing the Authorization header with the refresh token.
     * @param response the HttpServletResponse to write the new authentication response.
     * @throws TokenRefreshException if an input/output error occurs while updating the user's token.
     */
    void refreshToken(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws TokenRefreshException;
}
