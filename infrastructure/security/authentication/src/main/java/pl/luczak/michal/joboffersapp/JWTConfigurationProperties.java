package pl.luczak.michal.joboffersapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.jwt")
record JWTConfigurationProperties(
        String secret,
        long expirationTimeInMs,
        String issuer
) {
}
