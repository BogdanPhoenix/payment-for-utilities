package org.university.payment_for_utilities.services.implementations.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.exceptions.*;
import org.university.payment_for_utilities.pojo.requests.user.AuthenticationRequest;
import org.university.payment_for_utilities.pojo.requests.user.ChangePasswordRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.requests.user.UserRequest;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;
import org.university.payment_for_utilities.pojo.responses.user.UserResponse;
import org.university.payment_for_utilities.repositories.user.TokenRepository;
import org.university.payment_for_utilities.services.interfaces.JwtService;
import org.university.payment_for_utilities.services.interfaces.user.RegisteredUserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(UserEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RegisteredUserServiceTest {
    private static final String TOKEN_TEMPLATE = "^[\\w-]*\\.[\\w-]*\\.[\\w-]*$";
    private static final String HEADER_START = "Bearer ";

    @Autowired
    private RegisteredUserService service;

    @Autowired
    @Qualifier("registeredUserIvanRequest")
    private RegisteredUserRequest userIvanRequest;
    @Autowired
    @Qualifier("registeredUserOlegRequest")
    private RegisteredUserRequest userOlegRequest;
    @Autowired
    @Qualifier("authenticationUserIvanRequest")
    private AuthenticationRequest authenticationUserIvanRequest;
    @Autowired
    @Qualifier("changePasswordRequest")
    private ChangePasswordRequest changePasswordRequest;

    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private JwtService jwtService;

    private @NonNull UserRequest updateData() {
        return UserRequest
                .builder()
                .username(userIvanRequest.getUsername())
                .role(userIvanRequest.getRole())
                .build();
    }

    @Test
    @DisplayName("Checking the user's successful registration in the system.")
    void testRegistrationCorrect() {
        var response = service.registration(userIvanRequest);
        var responseUser = service.getByUsername(userIvanRequest.getUsername());

        checkAuthenticationResponse(response);
        assertNotNull(responseUser);
    }

    @Test
    @DisplayName("Checking for an exception when the request is empty inside.")
    void testRegistrationThrowEmptyRequestException() {
        var emptyRequest = RegisteredUserRequest.empty();
        assertThrows(EmptyRequestException.class,
                () -> service.registration(emptyRequest)
        );
    }

    @ParameterizedTest
    @MethodSource("testUserEmails")
    @DisplayName("Check exceptions if the request has an incorrect email format.")
    void testValidateUserEmailThrowInvalidInputDataException(String email) {
        userIvanRequest.setUsername(email);
        assertThrows(InvalidInputDataException.class,
                () -> service.registration(userIvanRequest)
        );
    }

    private static @NonNull Stream<Arguments> testUserEmails() {
        return Stream.of(
                Arguments.of("test_gmail.com"),
                Arguments.of("test@gmailcom"),
                Arguments.of("test@gmail_com"),
                Arguments.of("test1gmail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("testPasswords")
    @DisplayName("Check exceptions if the request has an incorrect password format.")
    void testValidatePasswordThrowInvalidInputDataException(String password) {
        userIvanRequest.setPassword(password);
        assertThrows(InvalidInputDataException.class,
                () -> service.registration(userIvanRequest)
        );
    }

    private static @NonNull Stream<Arguments> testPasswords() {
        return Stream.of(
                Arguments.of("qwE@t1"),
                Arguments.of("qwertyui"),
                Arguments.of("qwe3#yui"),
                Arguments.of("qWert4tdfgd")
        );
    }

    @Test
    @DisplayName("Check whether the values were successfully assigned to the createDate and updateDate attributes when the entity was created.")
    void testCreateDate() {
        var date = LocalDateTime.now();

        service.registration(userIvanRequest);
        var response = service.getByUsername(userIvanRequest.getUsername());

        assertThat(response.getCreateDate())
                .isNotNull()
                .isEqualToIgnoringNanos(response.getUpdateDate())
                .isEqualToIgnoringNanos(date);
        assertNotNull(response.getUpdateDate());
    }

    @Test
    @DisplayName("Checking the user's successful authentication in the system.")
    void testAuthenticateCorrect() {
        service.registration(userIvanRequest);

        await()
                .atMost(500L, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    var response = service.authenticate(authenticationUserIvanRequest);
                    checkAuthenticationResponse(response);
                });
    }

    private void checkAuthenticationResponse(AuthenticationResponse response) {
        assertNotNull(response);
        assertTrue(response
                .getAccessToken()
                .matches(TOKEN_TEMPLATE)
        );
        assertTrue(response
                .getRefreshToken()
                .matches(TOKEN_TEMPLATE)
        );
    }

    @Test
    @DisplayName("Checking for an exception when an unregistered user tries to log in.")
    void testAuthenticateThrowNotFindEntityInDataBaseException() {
        assertThrows(BadCredentialsException.class,
                () -> service.authenticate(authenticationUserIvanRequest)
        );
    }

    @Test
    @DisplayName("Checking the successful update of the registered user's token.")
    void testRefreshTokenCorrect() {
        var authenticationResponse = service.registration(userIvanRequest);
        var authHeader = HEADER_START + authenticationResponse.getAccessToken();

        await()
                .atMost(1000L, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    var response = service.refreshToken(authHeader);

                    assertEquals(authHeader, response.getRefreshToken());
                    assertNotNull(response.getAccessToken());
                });
    }

    @ParameterizedTest
    @MethodSource("testInputHeaders")
    @DisplayName("Check when an empty header or a missing token was sent in the request.")
    void testRefreshTokenInvalidHeader(String header) {
        assertThrows(TokenRefreshException.class,
                () -> service.refreshToken(header)
        );
    }

    private static @NonNull Stream<Arguments> testInputHeaders() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of("InvalidHeader")
        );
    }

    @Test
    @DisplayName("Check when it failed to retrieve the username from the received refreshToken.")
    void testRefreshTokenInvalidUser() throws IOException {
        when(request.getHeader(anyString())).thenReturn("Bearer validToken");
        when(jwtService.extractUsername(anyString())).thenReturn(null);

        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(tokenRepository, never()).save(any());
        verify(response, never()).getOutputStream();
    }

    @Test
    @DisplayName("Check when a newly generated refreshToken fails to validate for a registered user.")
    void testRefreshTokenIsNotTokenValid() throws IOException {
        var authenticationResponse = (AuthenticationResponse) service.registration(userIvanRequest);
        var authHeader = HEADER_START + authenticationResponse.getRefreshToken();

        when(request.getHeader(anyString())).thenReturn(authHeader);
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(false);

        verify(tokenRepository, never()).save(any());
        verify(response, never()).getOutputStream();
    }

    @Test
    @DisplayName("Check if you can retrieve an entity from a table by its identifier.")
    void testGetByIdCorrect() {
        service.registration(userIvanRequest);
        assertNotNull(service.getByUsername(userIvanRequest.getUsername()));
    }

    @Test
    @DisplayName("Checking the correctness of user account deactivation using the provided ID.")
    void testDeactivateCorrectById() {
        var username = userIvanRequest.getUsername();
        service.registration(userIvanRequest);

        var findBeforeRemove = service.getByUsername(username);
        var responseRemove = service.deactivate(findBeforeRemove.getId());

        assertNotNull(findBeforeRemove);
        assertNotNull(responseRemove);
        assertThrows(
                NotFindEntityInDataBaseException.class,
                () -> service.getByUsername(username)
        );
    }

    @Test
    @DisplayName("Check for an exception when an attempt is made to deactivate an account with an identifier that either does not exist in the system or has already been deactivated.")
    void testDeactivateByIdThrowNotFindEntityInDataBaseException() {
        var userId = 1L;
        assertThrows(
                NotFindEntityInDataBaseException.class,
                () -> service.deactivate(userId)
        );
    }

    @Test
    @DisplayName("Checking the correctness of deactivating a user account at the request.")
    void testDeactivateCorrectByRequest() {
        var username = userIvanRequest.getUsername();
        service.registration(userIvanRequest);

        var findBeforeRemove = service.getByUsername(username);
        var responseRemove = service.deactivate(userIvanRequest);

        assertNotNull(findBeforeRemove);
        assertNotNull(responseRemove);
        assertThrows(
                NotFindEntityInDataBaseException.class,
                () -> service.getByUsername(username)
        );
    }

    @Test
    @DisplayName("Check for an exception when an attempt is made to deactivate an account by a specified request that either does not exist in the system or has already been deactivated.")
    void testDeactivateByRequestThrowNotFindEntityInDataBaseException() {
        assertThrows(
                NotFindEntityInDataBaseException.class,
                () -> service.deactivate(userIvanRequest)
        );
    }

    @Test
    @DisplayName("Check that all data has been deactivated in the table.")
    void testDeactivateAll(){
        service.registration(userIvanRequest);
        service.registration(userOlegRequest);

        var numDeleteItems = service.deactivateAll();

        assertThat(numDeleteItems)
                .isEqualTo(2L);
    }

    @Test
    @DisplayName("Checking the correctness of changing the user account password.")
    void testChangePasswordCorrect() {
        var response = service.registration(userIvanRequest);
        var token = HEADER_START + response.getAccessToken();

        service.changePassword(changePasswordRequest, token);
    }

    @Test
    @DisplayName("Check if there is an exception when a token was transferred that did not pass verification.")
    void testChangePasswordThrowInvalidToken() {
        var invalidToken = "invalidToken";
        assertThrows(TokenRefreshException.class,
                () -> service.changePassword(changePasswordRequest, invalidToken)
        );
    }

    @Test
    @DisplayName("Check for an exception when an unregistered user was passed in the authentication data.")
    void testChangePasswordThrowNotFindEntityInDataBaseException() {
        var invalidToken = HEADER_START + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwODA3OTIzOSwiZXhwIjoxNzA4MTY1NjM5fQ.Gv_Vf1Dng5elpVJWHQinRxJsqSfqmprU1THBFoqdFogR1J-AHl4mQ3H2wgnUyiiuG-TiXX35AyKvkU4AnJCbpw";
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.changePassword(changePasswordRequest, invalidToken)
        );
    }

    @Test
    @DisplayName("Check for an exception when the \"current password\" in the variable request is not the same as the user's.")
    void testChangePasswordWrongPassword() {
        var response = service.registration(userIvanRequest);
        var token = HEADER_START + response.getAccessToken();
        changePasswordRequest.setCurrentPassword("fail_password");

        assertThrows(IllegalStateException.class,
                () -> service.changePassword(changePasswordRequest, token)
        );
    }

    @Test
    @DisplayName("Check for an exception when \"new password\" and \"password confirmation\" in the request variables are different.")
    void testChangePasswordSamePassword() {
        var response = service.registration(userIvanRequest);
        var token = HEADER_START + response.getAccessToken();
        changePasswordRequest.setConfirmationPassword("another_password");

        assertThrows(IllegalStateException.class,
                () -> service.changePassword(changePasswordRequest, token)
        );
    }

    @Test
    @DisplayName("Check whether the registered user's data was successfully updated in the database table.")
    void testUpdateDataCorrect() {
        service.registration(userIvanRequest);
        var userResponse = (UserResponse) service.getByUsername(userIvanRequest.getUsername());

        var expectedResponse = UserResponse
                .builder()
                .id(userResponse.getId())
                .username("update@gmail.com")
                .role(Role.BANK_ADMIN)
                .build();
        var requestUpdate = UserRequest
                .builder()
                .username(expectedResponse.getUsername())
                .role(expectedResponse.getRole())
                .build();

        var updateResponse = service.updateData(userResponse.getId(), requestUpdate);

        assertThat(updateResponse)
                .isEqualTo(expectedResponse)
                .isNotEqualTo(userResponse);
    }

    @Test
    @DisplayName("Checks for exceptions when a user wants to update data that is not in a database table in an update request.")
    void testUpdateValueThrowNotFindEntityInDataBase(){
        var empty = UserRequest.empty();
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.updateData(1L, empty)
        );
    }

    @Test
    @DisplayName("Check for an exception when an unvalidated username is passed in an update request.")
    void testUpdateValueThrowInvalidInputDataException() {
        service.registration(userIvanRequest);

        var userResponse = (UserResponse) service.getByUsername(userIvanRequest.getUsername());
        var userId = userResponse.getId();
        var updateRequest = updateData();
        updateRequest.setUsername("error username");

        assertThrows(InvalidInputDataException.class,
                () -> service.updateData(userId, updateRequest)
        );
    }

    @Test
    @DisplayName("Checks for exceptions when the update request passes a new area name that already exists in the database table.")
    void testUpdateValueThrowDuplicate(){
        service.registration(userIvanRequest);
        service.registration(userOlegRequest);

        var response = (UserResponse) service.getByUsername(userOlegRequest.getUsername());
        var user_id = response.getId();
        var updateRequest = updateData();

        assertThrows(DuplicateException.class,
                () -> service.updateData(user_id, updateRequest)
        );
    }

    @Test
    @DisplayName("Checks for an exception when an attempt is made to update user data using an ID that does not have an active user.")
    void testUpdateValueThrowNotFindEntityInDataBaseException() {
        var updateRequest = updateData();
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.updateData(Long.MAX_VALUE, updateRequest));
    }
}
