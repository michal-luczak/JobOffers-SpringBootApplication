package pl.luczak.michal.joboffersapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
class JWTAuthenticatorConfig {

    @Bean
    JWTAuthenticator jwtAuthenticator(AuthenticationManager authenticationManager) {
        return new JWTAuthenticator(authenticationManager);
    }
}
