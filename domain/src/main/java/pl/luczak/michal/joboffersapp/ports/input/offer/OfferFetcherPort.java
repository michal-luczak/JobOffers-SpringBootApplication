package pl.luczak.michal.joboffersapp.ports.input.offer;

import pl.luczak.michal.joboffersapp.offer.Fetchable;

import java.util.List;

/**
    @param <T> OfferDTO that is fetched by fetchOffers()
**/
public interface OfferFetcherPort<T extends Fetchable> {

    List<T> fetchOffers();
}
