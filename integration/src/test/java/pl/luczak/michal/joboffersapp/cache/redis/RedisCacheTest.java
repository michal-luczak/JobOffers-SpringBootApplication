package pl.luczak.michal.joboffersapp.cache.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.OfferServiceCacheableWrapper;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class RedisCacheTest extends AbstractIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @DynamicPropertySource
    private static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.data.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.data.redis.host", REDIS::getHost);
        registry.add("spring.data.redis.time-to-live", () -> "PT1S");
        registry.add("spring.cache.type", () -> "redis");
    }

    @SpyBean
    private OfferServiceCacheableWrapper offerServiceCacheableWrapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private OfferService offerService;

    @Test
    void should_successfully_cache_offers_and_then_remove_from_cache() throws Exception {
        // GIVEN && WHEN
        mockMvc.perform(get("/offers"));

        // THEN
        verify(offerServiceCacheableWrapper, times(1))
                .getCacheableOffers();
        assertThat(cacheManager.getCacheNames()).contains("offers");
        await().atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers"));
                            verify(offerServiceCacheableWrapper, atLeast(3))
                                    .getCacheableOffers();
                        }
                );
    }

    @Test
    void should_successfully_cache_offer_and_then_remove_from_cache() throws Exception {
        // GIVEN
        UUID uniqueID = UUID.randomUUID();
        OfferDTO offerDTO = OfferDTO.builder().uniqueID(uniqueID).build();

        // WHEN
        offerService.saveOffer(offerDTO);
        mockMvc.perform(get("/offers/" + uniqueID));

        // THEN
        verify(offerServiceCacheableWrapper, times(1))
                .getCacheableOfferById(uniqueID);
        assertThat(cacheManager.getCacheNames()).contains("offers");
        await().atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers/" + uniqueID));
                            verify(offerServiceCacheableWrapper, atLeast(3))
                                    .getCacheableOfferById(uniqueID);
                        }
                );
    }
}
