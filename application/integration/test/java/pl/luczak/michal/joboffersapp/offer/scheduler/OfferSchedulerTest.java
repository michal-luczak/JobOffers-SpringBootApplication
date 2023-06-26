package pl.luczak.michal.joboffersapp.offer.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferFetcherPort;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestPropertySource(properties = "spring.task.scheduling.enable=true")
class OfferSchedulerTest extends AbstractIntegrationTest {

    @SpyBean
    OfferFetcherPort<?> offerFetcher;

    @Test
    @DirtiesContext
    void scheduler_should_invoke_scheduled_method_one_time_at_least() {
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(offerFetcher, times(1)).fetchOffers());
    }
}