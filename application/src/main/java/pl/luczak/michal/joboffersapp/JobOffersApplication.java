package pl.luczak.michal.joboffersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.luczak.michal.joboffersapp.resttemplate.RestTemplateConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        OfferFetcherConfigProperties.class,
        RestTemplateConfigProperties.class,
        JWTConfigurationProperties.class
})
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
