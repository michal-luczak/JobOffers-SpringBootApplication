package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferDAO;

import java.util.*;
import java.util.stream.Collectors;

class InMemoryOfferPersistenceAdapter implements OfferDAO {

    private final Map<UUID, OfferDTO> offers = new HashMap<>();

    @Override
    public UUID saveOffer(OfferDTO offerDTO) {
        offers.put(offerDTO.uniqueID(), offerDTO);
        return offerDTO.uniqueID();
    }

    @Override
    public UUID deleteOffer(OfferDTO offerDTO) {
        offers.remove(offerDTO.uniqueID());
        return offerDTO.uniqueID();
    }

    @Override
    public Optional<OfferDTO> findOfferById(UUID uniqueID) {
        return offers.values()
                .stream()
                .filter(offerDTO -> offerDTO.uniqueID().equals(uniqueID))
                .findFirst();
    }

    @Override
    public List<OfferDTO> findAllOffers() {
        return new ArrayList<>(offers.values());
    }

    @Override
    public List<OfferDTO> saveAllOffers(List<OfferDTO> offerDTOs) {
        offerDTOs.forEach(offerDTO -> {
            offers.put(offerDTO.uniqueID(), offerDTO);
        });
        return offerDTOs;
    }

    @Override
    public Optional<OfferDTO> findOfferByUrl(String url) {
        return offers.values()
                .stream()
                .filter(offerDTO -> offerDTO.url().equals(url))
                .findFirst();
    }
}
