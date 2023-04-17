package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.ArrayList;
import java.util.List;

class OfferFetcherAdapter implements OfferFetcherPort {

    private final List<OfferDTO> offerDTOS = new ArrayList<>();

    @Override
    public List<OfferDTO> fetchOffers() {
        return offerDTOS;
    }

    void removeOffer(OfferDTO offerDTO) {
        offerDTOS.remove(offerDTO);
    }

    void addOffer(OfferDTO offerDTO) {
        offerDTOS.add(offerDTO);
    }
}
