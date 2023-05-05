package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.UUID;

public interface OfferController<T> {

    T saveOffer(OfferDTO offerDTO);

    T findAllOffers();

    T findOfferById(UUID uniqueID);
}
