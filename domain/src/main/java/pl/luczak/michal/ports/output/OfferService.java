package pl.luczak.michal.ports.output;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.List;
import java.util.UUID;

public interface OfferService {

    UUID saveOffer(OfferDTO offerDTO);

    List<OfferDTO> findAllOffers();

    OfferDTO findOfferById(UUID uniqueId);

    List<OfferDTO> fetchAllOffersAndSaveAllIfNotExists();
}
