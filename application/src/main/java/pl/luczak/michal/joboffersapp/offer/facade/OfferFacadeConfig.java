package pl.luczak.michal.joboffersapp.offer.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.dto.OfferRequestDTO;
import pl.luczak.michal.joboffersapp.offer.OfferFacade;
import pl.luczak.michal.joboffersapp.ports.input.OfferDAOPort;
import pl.luczak.michal.joboffersapp.ports.input.OfferFetcherPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

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
