package pl.luczak.michal.offer.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.offer.OfferFacade;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferDAO;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
class OfferFacadeConfig {

    @Bean
    OfferDAO offerDAO() {
        return new OfferDAO() {
            @Override
            public UUID saveOffer(OfferDTO offerDTO) {
                return null;
            }

            @Override
            public UUID deleteOffer(OfferDTO offerDTO) {
                return null;
            }

            @Override
            public Optional<OfferDTO> findOfferById(UUID uniqueID) {
                return Optional.empty();
            }

            @Override
            public List<OfferDTO> findAllOffers() {
                return null;
            }

            @Override
            public List<UUID> saveAllOffers(List<OfferDTO> offerDTOs) {
                return null;
            }

            @Override
            public Optional<OfferDTO> findOfferByUrl(String url) {
                return Optional.empty();
            }
        };
    }

    @Bean
    OfferFacade offerFacade(OfferDAO offerDAO, OfferFetcherPort offerFetcherPort) {
        return new OfferFacade(offerDAO, offerFetcherPort);
    }
}
