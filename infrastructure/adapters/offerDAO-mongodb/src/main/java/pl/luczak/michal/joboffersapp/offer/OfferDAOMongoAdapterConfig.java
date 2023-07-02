package pl.luczak.michal.joboffersapp.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories
class OfferDAOMongoAdapterConfig {

    @Bean
    OfferDAOMongoAdapter offerDaoMongoAdapter(
            OfferRepository offerRepository,
            IOfferDTOMapper<OfferDocument> offerDTOMapper
    ) {
        return new OfferDAOMongoAdapter(offerRepository, offerDTOMapper);
    }

    @Bean
    IOfferDTOMapper<OfferDocument> offerDocumentToOfferDTOMapper() {
        return new OfferDTOMapper();
    }
}
