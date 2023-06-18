package pl.luczak.michal.joboffersapp.offer.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "${job-offers.offer.scheduler.enable}")
class OfferSchedulerConfig {
}
