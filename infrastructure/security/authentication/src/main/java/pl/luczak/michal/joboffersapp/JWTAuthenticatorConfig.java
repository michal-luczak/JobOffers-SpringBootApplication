package pl.luczak.michal.joboffersapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.Clock;

@Configuration
class JWTAuthenticatorConfig {

    @Bean
    JWTAuthenticator jwtAuthenticator(
            AuthenticationManager authenticationManager,
            Clock clock,
            JWTConfigurationProperties jwtConfigurationProperties) {
        return new JWTAuthenticator(authenticationManager, clock, jwtConfigurationProperties);
    }
}
