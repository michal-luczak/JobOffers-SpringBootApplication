package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class OfferServiceCacheableWrapper {

    private final OfferService offerService;

    @Cacheable("offers")
    public List<OfferDTO> getCacheableOffers() {
        return offerService.findAllOffers();
    }

    @Cacheable("offers")
    public OfferDTO getCacheableOfferById(UUID uniqueID) {
        return offerService.findOfferById(uniqueID);
    }

    @CacheEvict(value = "offers", allEntries = true)
    public void clearOffersCache() {
        log.info("Cache has been cleared.");
    }
}
