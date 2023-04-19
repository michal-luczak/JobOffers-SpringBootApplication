package pl.luczak.michal.offer.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.time.Duration;

@Configuration
class OfferFetcherConfig {

    private static final String TEMPLATE = "job-offers.offer-fetcher.http-client.config";
    private static final String CONNECTION_TIME_OUT_PATH = "${" + TEMPLATE + ".connection-TimeOut}";
    private static final String READ_TIME_OUT_PATH = "${" + TEMPLATE + ".read-TimeOut}";
    private static final String URI_PATH = "${" + TEMPLATE + ".uri}";
    private static final String PORT_PATH = "${" + TEMPLATE + ".port}";

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    RestTemplate restTemplate(
            @Value(CONNECTION_TIME_OUT_PATH) long connectionTimeOut,
            @Value(READ_TIME_OUT_PATH) long readTimeOut,
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler
    ) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeOut))
                .setReadTimeout(Duration.ofMillis(readTimeOut))
                .build();
    }

    @Bean
    OfferFetcherPort offerFetcher(
            RestTemplate restTemplate,
            @Value(URI_PATH) String uri,
            @Value(PORT_PATH) int port
    ) {
        return new OfferFetcher(restTemplate, uri, port);
    }
}
