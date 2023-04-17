package pl.luczak.michal.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferDAO;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OfferFacade {

    private final OfferDAO offerDAO;

    public List<OfferDTO> findAllOffers() {
        return offerDAO.findAllOffers();
    }

    public UUID saveOffer(OfferDTO offerDTO) {
        return offerDAO.saveOffer(offerDTO);
    }

    public OfferDTO findOfferById(UUID uniqueID) {
        return offerPersistencePort.findOfferById(uniqueID)
                .orElseThrow(() -> new OfferNotFoundException(uniqueID));
    }

    public List<OfferDTO> fetchAllOffersAndSaveAllIfNotExists() {
        List<OfferDTO> fetchedOffers = offerFetcherPort.fetchOffers();
        offerPersistencePort.saveAllOffers(fetchedOffers);
        return fetchedOffers;
    }
}

