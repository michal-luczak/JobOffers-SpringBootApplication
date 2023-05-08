package pl.luczak.michal.joboffersapp.ports.output;

import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.List;
import java.util.UUID;

public interface OfferService {

    UUID saveOffer(OfferDTO offerDTO);

    List<OfferDTO> findAllOffers();

    OfferDTO findOfferById(UUID uniqueId);

    List<OfferDTO> fetchAllOffersAndSaveAllIfNotExists();
}
