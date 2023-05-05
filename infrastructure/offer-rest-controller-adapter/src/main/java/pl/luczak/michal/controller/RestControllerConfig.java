package pl.luczak.michal.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import pl.luczak.michal.offer.OfferController;
import pl.luczak.michal.ports.output.OfferService;

@Configuration
class RestControllerConfig {

    @Bean
    OfferController<ResponseEntity<?>> offerController(OfferService offerService) {
        return new RestOfferController(offerService);
    }
}
