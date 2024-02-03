package org.university.payment_for_utilities.services.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    /**
     * Extracts the username from a given JWT (JSON Web Token).
     *
     * @param token the JWT from which to extract the username.
     * @return the extracted username.
     */
    String extractUsername(String token);

    /**
     * Generic method to extract a specific claim from a JWT using a provided claims resolver function.
     *
     * @param <T>            the type of the claim to be extracted.
     * @param token          the JWT from which to extract the claim.
     * @param claimsResolver the function to resolve the desired claim from the JWT's claims set.
     * @return the extracted claim of type T.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Generates a JWT (JSON Web Token) based on the provided UserDetails.
     * This method uses default claims and expiration time for the token.
     *
     * @param userDetails the UserDetails containing information about the user.
     * @return the generated JWT.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT (JSON Web Token) with additional claims based on the provided UserDetails.
     *
     * @param extraClaims   additional claims to be included in the JWT.
     * @param userDetails   the UserDetails containing information about the user.
     * @return the generated JWT.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Generates a refresh token based on the provided UserDetails.
     * This method uses default claims and expiration time for the refresh token.
     *
     * @param userDetails the UserDetails containing information about the user.
     * @return the generated refresh token.
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * Validates the provided JWT (JSON Web Token) against the given UserDetails.
     *
     * @param token       the JWT to be validated.
     * @param userDetails the UserDetails of the user for validation.
     * @return true if the token is valid for the provided user, false otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
