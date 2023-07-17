package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.client.WireMockBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pl.luczak.michal.joboffersapp.dto.OfferRequestDTO;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OfferFetcherTest implements SamplesOffersResponse {

    private OfferFetcher offerFetcher;
    private List<OfferRequestDTO> offerRequestDTOList;

    @RegisterExtension
    protected static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(9999))
            .build();

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        offerRequestDTOList = threeOfferDTO().stream()
                .map(o -> OfferRequestDTO.builder()
                        .url(o.url())
                        .companyName(o.companyName())
                        .jobName(o.jobName())
                        .salary(o.salary())
                        .build()
                ).toList();
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(offerRequestDTOList))));
        RestTemplate restTemplate = new RestTemplate();
        OfferFetcherConfigProperties offerFetcherConfigProperties = new OfferFetcherConfigProperties(
                9999, "http://localhost"
        );
        this.offerFetcher = new OfferFetcher(restTemplate, offerFetcherConfigProperties);
    }

    @Test
    void should_fetch_offers() {
        // GIVEN && WHEN
        List<OfferRequestDTO> offerRequestDTOS = offerFetcher.fetchOffers();

        // THEN
        assertThat(offerRequestDTOS)
                .containsExactlyInAnyOrderElementsOf(offerRequestDTOList);
    }
}