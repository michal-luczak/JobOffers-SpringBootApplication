package pl.luczak.michal.offer.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.luczak.michal.offer.http.dto.OfferFetcherConfigProperties;
import pl.luczak.michal.ports.OfferFetcherPort;

@Configuration
class OfferFetcherConfig {

    @Bean
    OfferFetcherPort offerFetcher(
            RestTemplate restTemplate,
            OfferFetcherConfigProperties offerFetcherConfigProperties
    ) {
        return new OfferFetcher(restTemplate, offerFetcherConfigProperties);
    }
}
