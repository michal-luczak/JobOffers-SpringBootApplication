package pl.luczak.michal.joboffersapp.offer;

import java.util.UUID;

public class OfferNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Offer with uniqueID: %s not found";

    OfferNotFoundException(UUID uniqueID) {
        super(String.format(MESSAGE, uniqueID.toString()));
    }
}
