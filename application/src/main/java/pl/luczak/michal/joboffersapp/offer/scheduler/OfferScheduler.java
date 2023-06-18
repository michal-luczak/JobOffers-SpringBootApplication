package pl.luczak.michal.joboffersapp.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferSchedulerPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;

@Component
@AllArgsConstructor
class OfferScheduler implements OfferSchedulerPort {

    private final OfferService offerService;

    @Override
    @Scheduled(fixedRateString = "${job-offers.offer.scheduler.fixed-rate}")
    public List<OfferDTO> fetchOffers() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists();
    }
}
