package pl.luczak.michal.joboffersapp.offer.scheduler;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

@Configuration
abstract class OfferSchedulerTestConfig {

    @Bean
    OfferService offerService() {
        return Mockito.mock(OfferService.class);
    }
}
