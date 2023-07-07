package pl.luczak.michal.joboffersapp.cache.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.CacheableFacade;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        registry.add("spring.data.redis.time-to-live", () -> "PT1S");
        registry.add("spring.data.redis.type", () -> "redis");
    }

    @SpyBean
    private CacheableFacade cacheableFacade;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private OfferService offerService;

    @Test
    void should_successfully_cache_offers_and_then_remove_from_cache() throws Exception {
        mockMvc.perform(get("/offers"));
        verify(cacheableFacade, times(1))
                .getCacheableOffers();
        assertThat(cacheManager.getCacheNames().contains("offers")).isTrue();
        await().atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers"));
                            verify(cacheableFacade, atLeast(3))
                                    .getCacheableOffers();
                        }
                );
    }

    @Test
    void should_successfully_cache_offer_and_then_remove_from_cache() throws Exception {
        UUID uniqueID = UUID.randomUUID();
        OfferDTO offerDTO = OfferDTO.builder().uniqueID(uniqueID).build();
        offerService.saveOffer(offerDTO);
        mockMvc.perform(get("/offers/" + uniqueID));
        verify(cacheableFacade, times(1))
                .getCacheableOffer(uniqueID);
        assertThat(cacheManager.getCacheNames().contains("offers")).isTrue();
        await().atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers/" + uniqueID));
                            verify(cacheableFacade, atLeast(3))
                                    .getCacheableOffer(uniqueID);
                        }
                );
    }
}
