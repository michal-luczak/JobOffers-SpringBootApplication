package pl.luczak.michal.ports;

import pl.luczak.michal.offer.dto.OfferRequestDTO;

import java.util.List;

public interface OfferFetcherPort {

    List<OfferRequestDTO> fetchOffers();
}
