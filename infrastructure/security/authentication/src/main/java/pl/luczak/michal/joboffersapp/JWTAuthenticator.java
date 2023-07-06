package pl.luczak.michal.joboffersapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.time.Clock;
import java.time.Instant;

@AllArgsConstructor
class JWTAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JWTConfigurationProperties properties;

    JWTResponseDTO authenticateAndGenerateToken(LoginRequestDTO loginRequestDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.username(),
                            loginRequestDTO.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Wrong password for user with username: " + loginRequestDTO.username()) {};
        }
        User user = (User) authentication.getPrincipal();
        String token = buildToken(user);
        return new JWTResponseDTO(user.getUsername(), token);
    }

    private String buildToken(User user) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = clock.instant();
        Instant expiresAt = now.plusMillis(properties.expirationTimeInMs());
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
