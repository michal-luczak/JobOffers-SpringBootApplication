package pl.luczak.michal.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luczak.michal.offer.OfferFacade;

@Component
@AllArgsConstructor
class OfferScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(cron = "${job-offers.offer.scheduler.cron}")
    void fetchOffer() {
        offerFacade.fetchAllOffersAndSaveAllIfNotExists();
    }
}
