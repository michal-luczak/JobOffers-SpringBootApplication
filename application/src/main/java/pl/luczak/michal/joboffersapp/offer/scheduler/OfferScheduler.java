package pl.luczak.michal.joboffersapp.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferSchedulerPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;

@AllArgsConstructor
@Log4j2
class OfferScheduler implements OfferSchedulerPort {

    private final OfferService offerService;

    @Override
    @Scheduled(fixedRateString = "${job-offers.offer.scheduler.fixed-rate}")
    public List<OfferDTO> fetchOffers() {
        log.info("Scheduler has just run the task");
        List<OfferDTO> list = offerService.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Scheduler task has been completed successfully");
        return list;
    }
}
