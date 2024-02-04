package org.university.payment_for_utilities.configurations.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.university.payment_for_utilities.configurations.secutiry.JwtAuthenticationFilter;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.domains.user.Token;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.enumarations.TokenType;
import org.university.payment_for_utilities.repositories.user.TokenRepository;
import org.university.payment_for_utilities.services.interfaces.JwtService;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class JwtAuthenticationFilterTest {
    private static final String SIGN_IN_URL = "/payment_for_utilities/auth";
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final String TEST_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNzA0NDgyMywiZXhwIjoxNzA3MTMxMjIzfQ.keWEm9LlTteTO5L19f_CX6OdAlZM_sGg_smqMe90wAN2rUoEzC5g7s__oQvHmSfs8u07VcXjd76HjSksz7aP7A";
    private static final String USER_NAME = "test@gmail.com";

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("Checks if the filter is executed when the servlet contains the program path.")
    void testServletPathHasSignInURL() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn(SIGN_IN_URL);
        doNothing().when(filterChain).doFilter(request, response);

        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Valid Path with Null Authorization Header")
    void testValidPathWithNullAuthorizationHeader() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("test value");
        when(request.getHeader(AUTH_HEADER_NAME)).thenReturn(null);
        doNothing().when(filterChain).doFilter(request, response);

        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Valid Token Authentication and Authorization")
    void testValidTokenAuthenticationAndAuthorization() throws ServletException, IOException {
        var user = RegisteredUser
                .builder()
                .username(USER_NAME)
                .password("")
                .role(Role.USER)
                .phoneNum(new PhoneNum())
                .build();

        var token = Token
                .builder()
                .accessToken(TEST_TOKEN)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        when(request.getServletPath()).thenReturn("test value");
        when(request.getHeader(AUTH_HEADER_NAME)).thenReturn(AUTH_HEADER_PREFIX + TEST_TOKEN);
        when(jwtService.extractUsername(anyString())).thenReturn(USER_NAME);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(tokenRepository.findByAccessToken(anyString())).thenReturn(Optional.of(token));
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);

        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}
