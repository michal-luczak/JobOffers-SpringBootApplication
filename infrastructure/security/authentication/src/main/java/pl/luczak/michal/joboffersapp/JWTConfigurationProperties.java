package pl.luczak.michal.joboffersapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("job-offers.user.auth.jwt")
record JWTConfigurationProperties(
        String secret,
        long expirationTimeInMs,
        String issuer
) {
}
