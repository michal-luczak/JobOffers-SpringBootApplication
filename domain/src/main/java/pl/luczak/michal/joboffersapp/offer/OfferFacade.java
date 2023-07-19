package pl.luczak.michal.joboffersapp.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferDAOPort;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferFetcherPort;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OfferFacade<T extends Fetchable> implements OfferService {

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
        List<UUID> savedOffers = offerDAO.saveAllOffers(offerDTOS);
        return savedOffers.stream().map(this::findOfferById).toList();
    }
}

