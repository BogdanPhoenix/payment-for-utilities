package org.university.payment_for_utilities.services.implementations.user;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.user.RegisterUserResponse;
import org.university.payment_for_utilities.repositories.user.RegisteredUserRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.user.RegisterUserService;

import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class RegisterUserServiceImpl extends CrudServiceAbstract<RegisteredUser, RegisteredUserRepository> implements RegisterUserService {
    private static final String USER_EMAIL_TEMPLATE = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String PASSWORD_TEMPLATE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$&*_])[a-zA-Z\\d!@#$&*_]{8,}$";
    private static final String TEXT_MESSAGE_VALIDATE_EMAIL = """
            The email address you provided: "%s" has not been validated. It must comply with the following rules:
                - The main body must contain only letters (uppercase or lowercase) of the Latin alphabet, numbers, or the symbols '_', '.', '+', or '-'.
                - The '@' symbol must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, or a dash.
                - The dot (.) in the domain must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, a dash or a period.
            Your email address should look like: "username@domain.domain_zone"
            """;
    private static final String TEXT_MESSAGE_VALIDATE_PASSWORD = """
            The password you entered: "%s" has not been validated. It must meet the following rules:
                - Minimum 8 characters
                - At least one capital letter
                - At least one lowercase letter
                - At least one number
                - Allowed special characters: !@#$&*_
            """;

    protected RegisterUserServiceImpl(RegisteredUserRepository repository) {
        super(repository, "Registered users");
    }

    @Override
    protected RegisteredUser createEntity(Request request) {
        var userRequest = (RegisteredUserRequest) request;
        return RegisteredUser
                .builder()
                .userEmail(userRequest.getUserEmail())
                .passwordUser(userRequest.getPasswordUser())
                .phoneNum(userRequest.getPhoneNum())
                .build();
    }

    @Override
    protected RegisteredUser createEntity(Response response) {
        var userResponse = (RegisterUserResponse) response;
        var builder = RegisteredUser.builder();
        initEntityBuilder(builder, response);

        return builder
                .userEmail(userResponse.getUserEmail())
                .passwordUser(userResponse.getPasswordUser())
                .phoneNum(userResponse.getPhoneNum())
                .build();
    }

    @Override
    protected Response createResponse(RegisteredUser entity) {
        var builder = RegisterUserResponse.builder();
        initResponseBuilder(builder, entity);

        return builder
                .userEmail(entity.getUserEmail())
                .passwordUser(entity.getPasswordUser())
                .phoneNum(entity.getPhoneNum())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull RegisteredUser entity, @NonNull Request request) {
        var newValue = (RegisteredUserRequest) request;

        if(!newValue.getUserEmail().isBlank()){
            entity.setUserEmail(newValue.getUserEmail());
        }
        if(!newValue.getPasswordUser().isBlank()){
            entity.setPasswordUser(newValue.getPasswordUser());
        }
        if(!newValue.getPhoneNum().isEmpty()){
            entity.setPhoneNum(newValue.getPhoneNum());
        }
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var userRequest = (RegisteredUserRequest) request;

        validateUserEmail(userRequest.getUserEmail());
        validatePassword(userRequest.getPasswordUser());
    }

    private void validateUserEmail(@NonNull String userEmail) throws InvalidInputDataException {
        if(isUserEmail(userEmail)){
            return;
        }

        var message = String.format(TEXT_MESSAGE_VALIDATE_EMAIL, userEmail);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isUserEmail(@NonNull String userEmail) {
        return userEmail.isBlank() || userEmail.
                matches(USER_EMAIL_TEMPLATE);
    }

    private void validatePassword(@NonNull String password) throws InvalidInputDataException {
        if(isPassword(password)){
            return;
        }

        var message = String.format(TEXT_MESSAGE_VALIDATE_PASSWORD, password);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isPassword(@NonNull String password) {
        return password.isBlank() || password
                .matches(PASSWORD_TEMPLATE);
    }

    @Override
    protected Optional<RegisteredUser> findEntity(@NonNull Request request) {
        var userRequest = (RegisteredUserRequest) request;
        return repository
                .findByUserEmail(
                        userRequest.getUserEmail()
                );
    }
}
