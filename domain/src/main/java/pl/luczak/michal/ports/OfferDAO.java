package pl.luczak.michal.ports;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferDAO {

    UUID saveOffer(OfferDTO offerDTO);

    UUID deleteOffer(OfferDTO offerDTO);

    Optional<OfferDTO> findOfferById(UUID uniqueID);

    List<OfferDTO> findAllOffers();

    List<OfferDTO> saveAllOffers(List<OfferDTO> offerDTOs);

    Optional<OfferDTO> findOfferByUrl(String url);
}
