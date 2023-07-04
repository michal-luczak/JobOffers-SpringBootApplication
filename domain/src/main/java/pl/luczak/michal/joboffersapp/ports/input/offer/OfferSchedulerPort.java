package pl.luczak.michal.joboffersapp.ports.input.offer;

import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.List;

public interface OfferSchedulerPort {

    List<OfferDTO> fetchOffers();
}
