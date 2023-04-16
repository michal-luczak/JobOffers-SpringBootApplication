package pl.luczak.michal.ports;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.List;

public interface OfferFetcherPort {

    List<OfferDTO> fetchOffers();
}
