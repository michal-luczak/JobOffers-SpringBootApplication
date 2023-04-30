package pl.luczak.michal.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferDAOPort;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OfferFacade<T extends Fetchable> {

    private final OfferDAOPort offerDAO;
    private final OfferFetcherPort<T> offerFetcherPort;

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
        List<T> fetchedOffers = offerFetcherPort.fetchOffers();
        List<OfferDTO> offerDTOS = fetchedOffers.stream()
                .map(Fetchable::toOfferDTO)
                .filter(offerDTO -> offerDAO.findOfferByUrl(offerDTO.url()).isEmpty())
                .toList();
        offerDAO.saveAllOffers(offerDTOS);
        return offerDTOS;
    }
}

