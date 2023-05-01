package pl.luczak.michal.ports;

import java.util.List;

/**
    @param <T> OfferDTO that is fetched by fetchOffers()
**/
public interface OfferFetcherPort<T> {

    List<T> fetchOffers();
}
