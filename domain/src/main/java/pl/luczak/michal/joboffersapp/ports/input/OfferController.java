package pl.luczak.michal.joboffersapp.ports.input;

import java.util.UUID;

/**
 * @param <RP> Response
 * @param <RQ> Request
**/

public interface OfferController<RP, RQ> {

    RP saveOffer(RQ rq);

    RP findAllOffers();

    RP findOfferById(UUID uniqueID);
}
