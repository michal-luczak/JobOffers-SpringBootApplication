package pl.luczak.michal.joboffersapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

@AllArgsConstructor
class JWTAuthenticator {

    private final AuthenticationManager authenticationManager;

    JWTResponseDTO authenticateAndGenerateToken(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.username(),
                        loginRequestDTO.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = buildToken(user);
        return new JWTResponseDTO(user.getUsername(), token);
    }

    private String buildToken(User user) {
//        String secretKey = properties.secret();
        String secretKey = "test";
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
//        Instant expiresAt = now.plusMillis(properties.expirationTimeInMs());
//        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
//                .withIssuedAt(now)
//                .withExpiresAt(expiresAt)
//                .withIssuer(issuer)
                .sign(algorithm);
    }
}
