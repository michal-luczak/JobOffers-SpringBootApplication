package pl.luczak.michal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.luczak.michal.offer.http.OfferFetcherConfigProperties;
import pl.luczak.michal.offer.http.resttemplate.RestTemplateConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        OfferFetcherConfigProperties.class,
        RestTemplateConfigProperties.class
})
@EnableScheduling
@EnableMongoRepositories
@EnableJpaRepositories
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
