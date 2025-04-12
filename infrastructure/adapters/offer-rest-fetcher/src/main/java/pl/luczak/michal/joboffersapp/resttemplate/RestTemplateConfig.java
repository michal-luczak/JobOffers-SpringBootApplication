package pl.luczak.michal.joboffersapp.resttemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
                .connectTimeout(
                        Duration.ofMillis(restTemplateConfigProperties.connectionTimeOut())
                )
                .readTimeout(
                        Duration.ofMillis(restTemplateConfigProperties.readTimeOut())
                )
                .build();
    }
}
