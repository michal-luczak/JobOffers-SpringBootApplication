package pl.luczak.michal.joboffersapp.ports.input.offer;

import java.util.UUID;

/**
 * @param <RP> Response
 * @param <RQ> Request
**/

public interface OfferControllerPort<RP, RQ> {

    RP saveOffer(RQ rq);

    RP findAllOffers();

    RP findOfferById(UUID uniqueID);
}
