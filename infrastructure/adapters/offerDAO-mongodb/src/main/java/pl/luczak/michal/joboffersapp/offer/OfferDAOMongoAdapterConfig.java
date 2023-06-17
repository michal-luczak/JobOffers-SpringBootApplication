package pl.luczak.michal.joboffersapp.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
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
