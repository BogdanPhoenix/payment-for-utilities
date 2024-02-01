package org.university.payment_for_utilities.services.implementations.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.domains.user.Token;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.enumarations.TokenType;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.user.AuthenticationRequest;
import org.university.payment_for_utilities.pojo.requests.user.ChangePasswordRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.requests.user.UserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;
import org.university.payment_for_utilities.pojo.responses.user.UserResponse;
import org.university.payment_for_utilities.repositories.user.RegisteredUserRepository;
import org.university.payment_for_utilities.repositories.user.TokenRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.JwtService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.PhoneNumService;
import org.university.payment_for_utilities.services.interfaces.user.ContractEntityService;
import org.university.payment_for_utilities.services.interfaces.user.InfoAboutUserService;
import org.university.payment_for_utilities.services.interfaces.user.RegisteredUserService;

import java.io.IOException;
import java.util.Optional;

import static org.university.payment_for_utilities.services.implementations.tools.ExceptionTools.throwRuntimeException;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {
    private static final String HEADER_START_FROM = "Bearer ";
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

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final RegisteredUserCrudService crudService;

    @Autowired
    public RegisteredUserServiceImpl(
            RegisteredUserRepository repository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager,
            ContractEntityService contractEntityService,
            InfoAboutUserService infoAboutUserService,
            PhoneNumService phoneNumService
    ) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;

        this.crudService = new RegisteredUserCrudService(
                repository,
                passwordEncoder,
                contractEntityService,
                infoAboutUserService,
                phoneNumService
        );
    }

    @Transactional
    @Override
    public AuthenticationResponse registration(@NonNull RegisteredUserRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException {
        var responseRegistered = crudService.addValue(request);
        var registeredUser = crudService.createEntity(responseRegistered, request.getPassword());

        return createAuthenticationResponse(registeredUser);
    }

    @Override
    public AuthenticationResponse authenticate(@NonNull AuthenticationRequest request) throws BadCredentialsException {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        var user = crudService.findAuthenticatedUser(request);
        revokeAllUserTokens(user);

        return createAuthenticationResponse(user);
    }

    @Override
    public void refreshToken(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!hasValidHeader(authHeader)) {
            return;
        }

        var refreshToken = authHeader.substring(HEADER_START_FROM.length());
        var username = jwtService.extractUsername(refreshToken);

        if(!hasValidUser(username)) {
            return;
        }

        var authenticationRequest = AuthenticationRequest
                .builder()
                .username(username)
                .build();

        var user = crudService.findAuthenticatedUser(authenticationRequest);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            return;
        }

        revokeAllUserTokens(user);
        var authResponse = createAuthenticationResponse(user, refreshToken);

        new ObjectMapper()
                .writeValue(response.getOutputStream(), authResponse);
    }

    private boolean hasValidHeader(String header) {
        return header != null && header.startsWith(HEADER_START_FROM);
    }

    private boolean hasValidUser(String username) {
        return username != null && !username.isBlank();
    }

    private void revokeAllUserTokens(@NonNull RegisteredUser user) {
        var validUserTokens = tokenRepository
                .findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private AuthenticationResponse createAuthenticationResponse(@NonNull RegisteredUser user){
        var refreshToken = jwtService.generateRefreshToken(user);
        return this.createAuthenticationResponse(user, refreshToken);
    }

    private AuthenticationResponse createAuthenticationResponse(
            @NonNull RegisteredUser user,
            @NonNull String refreshToken
    ) {
        var jwtToken = jwtService.generateToken(user);

        saveUserToken(user, jwtToken);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(
            @NonNull RegisteredUser user,
            @NonNull String jwtToken
    ) {
        var token = Token
                .builder()
                .user(user)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    @Override
    public @NonNull Response getByUsername(@NonNull String username) throws NotFindEntityInDataBaseException {
        return crudService.getByUsername(username);
    }

    @Override
    public @NonNull Response deactivate(@NonNull Long id) {
        return crudService.removeValue(id);
    }

    @Override
    public void changePassword(
            @NonNull ChangePasswordRequest request,
            @NonNull Authentication authentication
    ) throws NotFindEntityInDataBaseException, InvalidAuthenticationData, IllegalStateException {
        crudService.changePassword(request, authentication);
    }

    @Override
    public @NonNull Response updateData(@NonNull Long id, @NonNull UserRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException {
        return crudService.updateValue(id, request);
    }

    private static class RegisteredUserCrudService extends CrudServiceAbstract<RegisteredUser, RegisteredUserRepository> {
        private final PasswordEncoder passwordEncoder;
        private final ContractEntityService contractEntityService;
        private final InfoAboutUserService infoAboutUserService;
        private final PhoneNumService phoneNumService;

        protected RegisteredUserCrudService(
                RegisteredUserRepository repository,
                PasswordEncoder passwordEncoder,
                ContractEntityService contractEntityService,
                InfoAboutUserService infoAboutUserService,
                PhoneNumService phoneNumService
        ) {
            super(repository, "Registered users");

            this.passwordEncoder = passwordEncoder;
            this.contractEntityService = contractEntityService;
            this.infoAboutUserService = infoAboutUserService;
            this.phoneNumService = phoneNumService;
        }

        @Override
        protected RegisteredUser createEntity(Request request) {
            var userRequest = (RegisteredUserRequest) request;
            return RegisteredUser
                    .builder()
                    .username(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .role(userRequest.getRole())
                    .phoneNum(userRequest.getPhoneNum())
                    .build();
        }

        protected RegisteredUser createEntity(Response response, String password) {
            var userResponse = (UserResponse) response;
            var builder = RegisteredUser.builder();
            initEntityBuilder(builder, response);

            return builder
                    .username(userResponse.getUsername())
                    .password(passwordEncoder.encode(password))
                    .role(userResponse.getRole())
                    .phoneNum(userResponse.getPhoneNum())
                    .build();
        }

        @Contract("_ -> new")
        @Override
        protected @NonNull RegisteredUser createEntity(Response response) {
            return RegisteredUser.empty();
        }

        @Override
        protected Response createResponse(RegisteredUser entity) {
            var builder = UserResponse.builder();
            initResponseBuilder(builder, entity);

            return builder
                    .username(entity.getUsername())
                    .role(entity.getRole())
                    .phoneNum(entity.getPhoneNum())
                    .build();
        }

        @Override
        protected void deactivatedChildren(@NonNull RegisteredUser entity) {
            deactivateChild(entity.getInfoUser(), infoAboutUserService);
            deactivateChildrenCollection(entity.getContractEntities(), contractEntityService);
            deactivateChild(entity.getPhoneNum(), phoneNumService);
        }

        @Override
        protected void updateEntity(@NonNull RegisteredUser entity, @NonNull Request request) {
            var newValue = (UserRequest) request;

            if(!newValue.getUsername().isBlank()){
                entity.setUsername(newValue.getUsername());
            }
            if(!newValue.getRole().equals(Role.EMPTY)){
                entity.setRole(newValue.getRole());
            }
            if(!newValue.getPhoneNum().isEmpty()){
                entity.setPhoneNum(newValue.getPhoneNum());
            }
        }

        @Override
        protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
            var userRequest = (UserRequest) request;
            validateUsername(userRequest.getUsername());

            if(userRequest instanceof RegisteredUserRequest registeredRequest){
                validatePassword(registeredRequest.getPassword());
            }
        }

        private void validateUsername(@NonNull String username) throws InvalidInputDataException {
            if(isUsername(username)){
                return;
            }

            var message = String.format(TEXT_MESSAGE_VALIDATE_EMAIL, username);
            throwRuntimeException(message, InvalidInputDataException::new);
        }

        private boolean isUsername(@NonNull String username) {
            return username.isBlank() || username.
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

        public Response getByUsername(@NonNull String username) throws NotFindEntityInDataBaseException {
            var request = AuthenticationRequest
                    .builder()
                    .username(username)
                    .build();

            var user = findAuthenticatedUser(request);
            return createResponse(user);
        }

        @Transactional
        public void changePassword(
                @NonNull ChangePasswordRequest request,
                @NonNull Authentication authentication
        ) throws NotFindEntityInDataBaseException, InvalidAuthenticationData, IllegalStateException {
            validateAuthentication(authentication);

            var userRequest = AuthenticationRequest
                    .builder()
                    .username((String) authentication.getPrincipal())
                    .password((String) authentication.getCredentials())
                    .build();
            var user = findAuthenticatedUser(userRequest);

            validateCurrentPassword(request.getCurrentPassword(), user.getPassword());
            validateNotSamePassword(request);

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
        }

        private void validateAuthentication(@NonNull Authentication authentication) throws InvalidAuthenticationData {
            if(authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {
                return;
            }

            var message = "The data you provide for authentication may not be used to verify your identity. Your username and password are required for authentication.";
            throwRuntimeException(message, InvalidAuthenticationData::new);
        }

        private void validateCurrentPassword(String currentPassword, String userPassword) throws IllegalStateException {
            if(passwordEncoder.matches(currentPassword, userPassword)){
                return;
            }

            throwRuntimeException("The \"current password\" you entered does not match the password for your current account.", IllegalStateException::new);
        }

        private void validateNotSamePassword(@NonNull ChangePasswordRequest request) throws IllegalStateException {
            if(request.getNewPassword().equals(request.getConfirmationPassword())){
                return;
            }

            throwRuntimeException("The passwords in the \"newPassword\" and \"confirmationPassword\" request variables are not the same.", IllegalStateException::new);
        }

        private RegisteredUser findAuthenticatedUser(@NonNull AuthenticationRequest request) throws NotFindEntityInDataBaseException {
            var requestRegistered = UserRequest
                    .builder()
                    .username(request.getUsername())
                    .build();

            return findOldEntity(requestRegistered);
        }

        @Override
        protected Optional<RegisteredUser> findEntity(@NonNull Request request) {
            var userRequest = (UserRequest) request;
            return repository
                    .findByUsername(
                            userRequest.getUsername()
                    );
        }
    }
}
