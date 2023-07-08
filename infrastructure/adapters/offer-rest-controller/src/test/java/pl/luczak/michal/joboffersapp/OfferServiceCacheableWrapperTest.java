package pl.luczak.michal.joboffersapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cache.annotation.Cacheable;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OfferServiceCacheableWrapperTest {

    private OfferServiceCacheableWrapper offerServiceCacheableWrapper;
    private OfferService offerService;

    @BeforeEach
    void setUp() {
        this.offerService = Mockito.spy(OfferService.class);
        this.offerServiceCacheableWrapper = new OfferServiceCacheableWrapper(offerService);
    }

    @Test
    void getCacheableOffers() throws NoSuchMethodException {
        Method method = OfferServiceCacheableWrapper.class
                .getMethod("getCacheableOffers");
        boolean isAnnotationInCacheable = method.isAnnotationPresent(Cacheable.class);
        offerServiceCacheableWrapper.getCacheableOffers();
        verify(offerService, times(1)).findAllOffers();
        assertThat(isAnnotationInCacheable).isTrue();
    }

    @Test
    void getCacheableOffer() throws NoSuchMethodException {
        Method method = OfferServiceCacheableWrapper.class
                .getMethod("getCacheableOffer", UUID.class);
        boolean isAnnotationInCacheable = method.isAnnotationPresent(Cacheable.class);
        offerServiceCacheableWrapper.getCacheableOffer(UUID.randomUUID());
        verify(offerService, times(1)).findOfferById(Mockito.any());
        assertThat(isAnnotationInCacheable).isTrue();
    }
}