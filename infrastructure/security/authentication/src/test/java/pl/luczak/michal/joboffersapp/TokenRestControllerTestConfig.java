package pl.luczak.michal.joboffersapp;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class TokenRestControllerTestConfig {

    @Bean
    TokenRestController tokenRestController(JWTAuthenticator jwtAuthenticator) {
        return new TokenRestController(jwtAuthenticator);
    }

    @Bean
    JWTAuthenticator jwtAuthenticator() {
        return Mockito.mock(JWTAuthenticator.class);
    }
}
