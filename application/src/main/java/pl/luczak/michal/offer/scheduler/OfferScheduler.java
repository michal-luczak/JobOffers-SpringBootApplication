package pl.luczak.michal.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luczak.michal.offer.OfferFacade;
import pl.luczak.michal.offer.http.dto.OfferRequestDTO;

@Component
@AllArgsConstructor
class OfferScheduler {

    private final OfferFacade<OfferRequestDTO> offerFacade;

    @Scheduled(cron = "${job-offers.offer.scheduler.cron}")
    void fetchOffer() {
        offerFacade.fetchAllOffersAndSaveAllIfNotExists();
    }
}
