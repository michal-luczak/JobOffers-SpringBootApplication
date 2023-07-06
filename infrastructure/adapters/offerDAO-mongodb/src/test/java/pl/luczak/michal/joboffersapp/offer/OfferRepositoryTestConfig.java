package pl.luczak.michal.joboffersapp.offer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableMongoAuditing
@Configuration
@SpringBootApplication
@EntityScan("pl.luczak.michal.joboffersapp.offer")
class OfferRepositoryTestConfig {

}
