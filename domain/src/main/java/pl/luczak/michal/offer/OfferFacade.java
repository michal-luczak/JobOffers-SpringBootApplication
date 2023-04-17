package pl.luczak.michal.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferDAO;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OfferFacade {

    private final OfferDAO offerDAO;
    private final OfferFetcherPort offerFetcherPort;

    public List<OfferDTO> findAllOffers() {
        return offerDAO.findAllOffers();
    }

    public UUID saveOffer(OfferDTO offerDTO) {
        return offerDAO.saveOffer(offerDTO);
    }

    public OfferDTO findOfferById(UUID uniqueID) {
        return offerDAO.findOfferById(uniqueID)
                .orElseThrow(() -> new OfferNotFoundException(uniqueID));
    }

    public List<OfferDTO> fetchAllOffersAndSaveAllIfNotExists() {
        List<OfferDTO> fetchedOffers = offerFetcherPort.fetchOffers();
        offerDAO.saveAllOffers(fetchedOffers);
        return fetchedOffers;
    }
}

