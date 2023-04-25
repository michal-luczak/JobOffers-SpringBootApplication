package pl.luczak.michal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.luczak.michal.offer.http.dto.OfferFetcherConfigProperties;
import pl.luczak.michal.offer.http.dto.RestTemplateConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        OfferFetcherConfigProperties.class,
        RestTemplateConfigProperties.class
})
@EnableScheduling
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
