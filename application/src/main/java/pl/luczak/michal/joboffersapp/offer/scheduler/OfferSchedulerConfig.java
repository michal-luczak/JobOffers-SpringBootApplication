package pl.luczak.michal.joboffersapp.offer.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferSchedulerPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

@Configuration
class OfferSchedulerConfig {

    @Bean
    @ConditionalOnProperty(name = "job-offers.offer.scheduler.enable")
    OfferSchedulerPort offerSchedulerPort(OfferService offerService) {
        return new OfferScheduler(offerService);
    }
}
