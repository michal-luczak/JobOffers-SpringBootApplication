package pl.luczak.michal.offer.http.resttemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.luczak.michal.offer.http.dto.RestTemplateConfigProperties;

import java.time.Duration;

@Configuration
class RestTemplateConfig {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    RestTemplate restTemplate(
            RestTemplateConfigProperties restTemplateConfigProperties,
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler
    ) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(
                        Duration.ofMillis(restTemplateConfigProperties.connectionTimeOut())
                )
                .setReadTimeout(
                        Duration.ofMillis(restTemplateConfigProperties.readTimeOut())
                )
                .build();
    }
}
