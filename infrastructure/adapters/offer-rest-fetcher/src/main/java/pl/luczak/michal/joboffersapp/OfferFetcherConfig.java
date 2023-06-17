package pl.luczak.michal.joboffersapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.luczak.michal.joboffersapp.dto.OfferRequestDTO;
import pl.luczak.michal.joboffersapp.ports.input.OfferFetcherPort;

@Configuration
class OfferFetcherConfig {

    @Bean
    OfferFetcherPort<OfferRequestDTO> offerFetcher(
            RestTemplate restTemplate,
            OfferFetcherConfigProperties offerFetcherConfigProperties
    ) {
        return new OfferFetcher(restTemplate, offerFetcherConfigProperties);
    }
}
