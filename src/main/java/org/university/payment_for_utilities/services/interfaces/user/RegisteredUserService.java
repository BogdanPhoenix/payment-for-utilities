package org.university.payment_for_utilities.services.interfaces.user;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.user.ChangePasswordRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.requests.user.UserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;

@Transactional
public interface RegisteredUserService extends AuthenticationService {
    /**
     * Retrieves user information based on the provided username.
     *
     * @param username the username of the user to retrieve.
     * @return a non-null Response containing user information.
     * @throws NotFindEntityInDataBaseException if no user is found in the database with the provided username.
     */
    @NonNull Response getByUsername(@NonNull String username) throws NotFindEntityInDataBaseException;

    /**
     * Deactivates the user account and all data associated with it based on the provided user ID.
     *
     * @param id unique identifier of the user to be deactivated.
     * @return a non-zero response value indicating that the deactivation was successful.
     */
    @NonNull Response deactivate(@NonNull Long id);

    /**
     * Deactivates the user account and all associated data based on the data provided in the request.
     *
     * @param request must not be {@literal null}.
     * @return response for the deactivate entity; never {@literal null}.
     * @throws NotFindEntityInDataBaseException if the table does not contain the entity to be deleted.
     */
    @NonNull Response deactivate(@NonNull RegisteredUserRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Deactivate all users in the table.
     *
     * @return the number of deactivated entities.
     */
    @NonNull Long deactivateAll();

    /**
     * Changes the password for the authenticated user based on the provided ChangePasswordRequest.
     *
     * @param request        the ChangePasswordRequest containing old and new password information.
     * @param refreshToken   a user token that initiates a password change.
     * @throws NotFindEntityInDataBaseException if the authenticated user is not found in the database.
     * @throws InvalidAuthenticationData      if the provided authentication data is invalid.
     * @throws TokenRefreshException           if there are problems with the transferred token.
     */
    AuthenticationResponse changePassword(
            @NonNull ChangePasswordRequest request,
            @NonNull String refreshToken
    ) throws TokenRefreshException, NotFindEntityInDataBaseException, IllegalStateException;

    /**
     * Updates user data based on the provided user ID and UserRequest.
     *
     * @param id      the unique identifier of the user to be updated.
     * @param request the UserRequest containing updated user information.
     * @return a non-null Response indicating the success of the data update.
     * @throws EmptyRequestException          if the update request is empty or contains null values.
     * @throws InvalidInputDataException      if the input data in the update request is invalid.
     * @throws DuplicateException             if the update results in duplicate data in the system.
     * @throws NotFindEntityInDataBaseException if the user to be updated is not found in the database.
     */
    @NonNull Response updateData(
            @NonNull Long id,
            @NonNull UserRequest request
    ) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException;
}
