package org.university.payment_for_utilities.configurations.secutiry;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.university.payment_for_utilities.enumarations.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private static final String DEFAULT_URL = "/payment-for-utilities";
    private static final String[] WHITE_LIST = {
            DEFAULT_URL,
            DEFAULT_URL + "/users/registration",
            DEFAULT_URL + "/users/auth"
    };

    private static final String[] USER_LIST = {
            DEFAULT_URL + "/*/users/**"
    };

    private static final String[] ADMIN_LIST = {
            DEFAULT_URL + "/*/admin/**"
    };

    private static final String[] BANK_ADMIN_LIST = {
            DEFAULT_URL + "/*/bank-admin/**"
    };

    private static final String[] COMPANY_ADMIN_LIST = {
            DEFAULT_URL + "/*/company-admin/**"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final String rememberMeKey;

    @Autowired
    public WebSecurityConfiguration(
            JwtAuthenticationFilter jwtAuthFilter,
            AuthenticationProvider authenticationProvider,
            LogoutHandler logoutHandler,
            Dotenv dotenv
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;

        this.rememberMeKey = dotenv.get("REMEMBER_ME_KEY");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(GET, USER_LIST).hasAnyAuthority(USER.name())
                        .requestMatchers(GET, USER_LIST).authenticated()
                        .requestMatchers(GET, ADMIN_LIST).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(GET, ADMIN_LIST).authenticated()
                        .requestMatchers(GET, BANK_ADMIN_LIST).hasAnyAuthority(BANK_ADMIN.name())
                        .requestMatchers(GET, BANK_ADMIN_LIST).authenticated()
                        .requestMatchers(GET, COMPANY_ADMIN_LIST).hasAnyAuthority(COMPANY_ADMIN.name())
                        .requestMatchers(GET, COMPANY_ADMIN_LIST).authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl(DEFAULT_URL)
                                .failureForwardUrl("/login?error=true")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                                .logoutSuccessUrl("/login?logout=true")
                )
                .rememberMe(remember ->
                        remember
                                .key(rememberMeKey)
                                .tokenValiditySeconds(604800)
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"))
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}