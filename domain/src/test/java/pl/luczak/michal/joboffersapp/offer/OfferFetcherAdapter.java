package pl.luczak.michal.joboffersapp.offer;

import pl.luczak.michal.joboffersapp.ports.input.OfferFetcherPort;

import java.util.ArrayList;
import java.util.List;

class OfferFetcherAdapter implements OfferFetcherPort<OfferFetchedDTO> {

    private final List<OfferFetchedDTO> offerDTOS = new ArrayList<>();

    @Override
    public List<OfferFetchedDTO> fetchOffers() {
        return offerDTOS;
    }

    void removeOffer(OfferFetchedDTO offerDTO) {
        offerDTOS.remove(offerDTO);
    }

    void addOffer(OfferFetchedDTO offerDTO) {
        offerDTOS.add(offerDTO);
    }
}
