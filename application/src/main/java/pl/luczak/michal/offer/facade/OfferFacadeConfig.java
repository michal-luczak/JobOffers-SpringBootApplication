package pl.luczak.michal.offer.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.offer.OfferFacade;
import pl.luczak.michal.offer.http.dto.OfferRequestDTO;
import pl.luczak.michal.ports.input.OfferDAOPort;
import pl.luczak.michal.ports.input.OfferFetcherPort;
import pl.luczak.michal.ports.output.OfferService;

@Configuration
class OfferFacadeConfig {

    @Bean
    OfferService offerFacade(
            OfferDAOPort offerDAO,
            OfferFetcherPort<OfferRequestDTO> offerFetcherPort
    ) {
        return new OfferFacade<>(offerDAO, offerFetcherPort);
    }
}
