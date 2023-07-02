package pl.luczak.michal.joboffersapp.offer.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferSchedulerPort;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {
        OfferSchedulerTestConfig.class,
        OfferSchedulerConfig.class,
        SchedulingConfig.class
})
@SpringBootTest(properties = "job-offers.offer.scheduler.fixed-rate=PT1S")
class OfferSchedulerTest {

    @SpyBean
    private OfferSchedulerPort offerScheduler;

    @Test
    void scheduler_should_invoke_scheduled_method_one_time_at_least() {
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(offerScheduler, atLeast(1)).fetchOffers());
    }
}