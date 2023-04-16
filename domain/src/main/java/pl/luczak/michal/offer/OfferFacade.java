package pl.luczak.michal.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferPersistencePort;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OfferFacade {

    private final OfferPersistencePort offerPersistencePort;

    public List<OfferDTO> findAllOffers() {
        return offerPersistencePort.findAllOffers();
    }

    public UUID saveOffer(OfferDTO offerDTO) {
        return offerPersistencePort.saveOffer(offerDTO);
    }

    public OfferDTO findOfferById(UUID uniqueID) {
        //TODO
        return null;
    }
}

