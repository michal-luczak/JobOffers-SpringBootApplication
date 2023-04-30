package pl.luczak.michal.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
class OfferDaoMongoAdapterConfig {

    @Bean
    OfferDAOMongoAdapter offerDaoMongoAdapter(
            OfferRepository offerRepository,
            OfferDTOMapper offerDTOMapper
    ) {
        return new OfferDAOMongoAdapter(offerRepository, offerDTOMapper);
    }
}
