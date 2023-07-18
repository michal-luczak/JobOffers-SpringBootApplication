package pl.luczak.michal.joboffersapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class JWTAuthenticatorTest {

    private static JWTAuthenticator jwtAuthenticator;
    private static final Instant instant = Instant.now();

    @BeforeAll
    static void setUp() {
        Clock clock = Mockito.mock(Clock.class);
        when(clock.instant()).thenReturn(instant);
        AuthenticationManager authenticationManager = new TestAuthenticationManagerMock();
        JWTConfigurationProperties jwtConfigurationProperties = new JWTConfigurationProperties(
                "testSecret",
                1000 * 60 * 60,
                "testIssuer"
        );
        jwtAuthenticator = new JWTAuthenticator(authenticationManager, clock, jwtConfigurationProperties);
    }

    @Test
    void should_successfully_return_token() {
        // GIVEN
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                "testUsername",
                "testPassword"
        );

        // WHEN
        JWTResponseDTO jwtResponseDTO = jwtAuthenticator.authenticateAndGenerateToken(loginRequestDTO);
        DecodedJWT decodedJWT = JWT.decode(jwtResponseDTO.token());

        // THEN
        assertAll(() -> {
            assertThat(jwtResponseDTO.username())
                    .isEqualTo(loginRequestDTO.username());
            assertThat(decodedJWT.getSubject())
                    .isEqualTo(loginRequestDTO.username());
            assertThat(decodedJWT.getIssuer())
                    .isEqualTo("testIssuer");
            assertThat(decodedJWT.getExpiresAtAsInstant())
                    .isEqualTo(instant.plus(1, ChronoUnit.HOURS)
                            .truncatedTo(ChronoUnit.SECONDS)
                    );
        });
    }

    @Test
    void should_throw_AuthenticationException() {
        // GIVEN
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                "wrongUsername",
                "testPassword"
        );

        // WHEN && THEN
        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> jwtAuthenticator.authenticateAndGenerateToken(loginRequestDTO)
        );
        assertThat(exception.getMessage())
                .isEqualTo(
                        String.format(
                            "User with username: %s not found",
                            loginRequestDTO.username()
                        )
                );
    }

    @Test
    void should_throw_BadCredentialsException() {

        // GIVEN
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                "testUsername",
                "wrongPassword"
        );

        // WHEN && THEN
        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> jwtAuthenticator.authenticateAndGenerateToken(loginRequestDTO)
        );
        assertThat(exception.getMessage())
                .isEqualTo("Wrong password for user with username: " + loginRequestDTO.username());
    }
}