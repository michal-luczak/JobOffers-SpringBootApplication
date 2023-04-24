package pl.luczak.michal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.luczak.michal.offer.http.OfferFetcherConfigProperties;
import pl.luczak.michal.offer.http.resttemplate.RestTemplateConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        OfferFetcherConfigProperties.class,
        RestTemplateConfigProperties.class
})
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
