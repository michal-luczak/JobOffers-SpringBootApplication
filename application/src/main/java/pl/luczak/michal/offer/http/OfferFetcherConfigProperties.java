package pl.luczak.michal.offer.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "job-offers.offer-fetcher.config")
public record OfferFetcherConfigProperties(
        @Name("port") int port,
        @Name("uri") String uri
) {}
