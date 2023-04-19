package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.offer.dto.OfferRequestDTO;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.ArrayList;
import java.util.List;

class OfferFetcherAdapter implements OfferFetcherPort {

    private final List<OfferRequestDTO> offerDTOS = new ArrayList<>();

    @Override
    public List<OfferRequestDTO> fetchOffers() {
        return offerDTOS;
    }

    void removeOffer(OfferRequestDTO offerDTO) {
        offerDTOS.remove(offerDTO);
    }

    void addOffer(OfferRequestDTO offerDTO) {
        offerDTOS.add(offerDTO);
    }
}
