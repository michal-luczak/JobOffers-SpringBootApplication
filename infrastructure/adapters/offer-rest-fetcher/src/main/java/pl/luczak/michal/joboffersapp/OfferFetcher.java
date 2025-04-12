package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.luczak.michal.joboffersapp.dto.OfferRequestDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferFetcherPort;

import java.util.List;

@AllArgsConstructor
@Log4j2
class OfferFetcher implements OfferFetcherPort<OfferRequestDTO> {

    private final RestTemplate restTemplate;
    private final OfferFetcherConfigProperties offerFetcherConfigProperties;

    @Override
    public List<OfferRequestDTO> fetchOffers() {
        log.warn("Fetching offers from external server...");
        String url = UriComponentsBuilder.fromUriString(getUrlService("/offers"))
                .toUriString();
        ResponseEntity<List<OfferRequestDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders()
                ),
                new ParameterizedTypeReference<>() {
                });
        log.info("Successfully fetched offers from external server");
        return response.getBody();
    }

    private String getUrlService(String service) {
        return offerFetcherConfigProperties.uri()
                        + ":"
                        + offerFetcherConfigProperties.port()
                        + service;
    }
}
