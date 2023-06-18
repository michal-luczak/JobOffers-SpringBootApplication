package pl.luczak.michal.joboffersapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "job-offers.offer.fetcher")
public record OfferFetcherConfigProperties(
        @Name("port") int port,
        @Name("uri") String uri
) {}
