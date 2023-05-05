package pl.luczak.michal.ports.input;

import pl.luczak.michal.offer.Fetchable;

import java.util.List;

/**
    @param <T> OfferDTO that is fetched by fetchOffers()
**/
public interface OfferFetcherPort<T extends Fetchable> {

    List<T> fetchOffers();
}
