package pl.luczak.michal.joboffersapp.ports.input.offer;

import java.util.UUID;

/**
 * @param <Response> Response
 * @param <Request> Request
**/

public interface OfferControllerPort<Response, Request> {

    Response saveOffer(Request rq);

    Response findAllOffers();

    Response findOfferById(UUID uniqueID);
}
