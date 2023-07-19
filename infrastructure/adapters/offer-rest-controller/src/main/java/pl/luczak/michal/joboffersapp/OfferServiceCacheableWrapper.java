package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
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
}
