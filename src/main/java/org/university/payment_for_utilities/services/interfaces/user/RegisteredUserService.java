package org.university.payment_for_utilities.services.interfaces.user;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.user.ChangePasswordRequest;
import org.university.payment_for_utilities.pojo.requests.user.UserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

public interface RegisteredUserService extends AuthenticationService {
    @NonNull Response getByUsername(@NonNull String username) throws NotFindEntityInDataBaseException;

    @NonNull Response deactivate(@NonNull Long id);

    void changePassword(
            @NonNull ChangePasswordRequest request,
            @NonNull Authentication authentication
    ) throws NotFindEntityInDataBaseException, InvalidAuthenticationData, IllegalStateException;

    @NonNull Response updateData(
            @NonNull Long id,
            @NonNull UserRequest request
    ) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException;
}
