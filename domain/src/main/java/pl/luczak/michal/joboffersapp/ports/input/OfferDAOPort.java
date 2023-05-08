package pl.luczak.michal.joboffersapp.ports.input;

import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferDAOPort {

    UUID saveOffer(OfferDTO offerDTO);

    UUID deleteOffer(OfferDTO offerDTO);

    Optional<OfferDTO> findOfferById(UUID uniqueID);

    List<OfferDTO> findAllOffers();

    List<UUID> saveAllOffers(List<OfferDTO> offerDTOs);

    Optional<OfferDTO> findOfferByUrl(String url);
}
