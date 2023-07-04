package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
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
class OfferFetcher implements OfferFetcherPort<OfferRequestDTO> {

    private final RestTemplate restTemplate;
    private final OfferFetcherConfigProperties offerFetcherConfigProperties;

    @Override
    public List<OfferRequestDTO> fetchOffers() {
        String url = UriComponentsBuilder.fromHttpUrl(getUrlService("/offers"))
                .toUriString();
        ResponseEntity<List<OfferRequestDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders()
                ),
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private String getUrlService(String service) {
        return offerFetcherConfigProperties.uri()
                        + ":"
                        + offerFetcherConfigProperties.port()
                        + service;
    }
}
