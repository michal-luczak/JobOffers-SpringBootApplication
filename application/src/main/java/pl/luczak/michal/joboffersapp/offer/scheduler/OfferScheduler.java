package pl.luczak.michal.joboffersapp.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

@Component
@AllArgsConstructor
class OfferScheduler {

    private final OfferService offerService;

    @Scheduled(fixedRateString = "${job-offers.offer.scheduler.fixed-rate}")
    void fetchOffer() {
        offerService.fetchAllOffersAndSaveAllIfNotExists();
    }
}
