package pl.luczak.michal.offer;

import java.util.UUID;

class OfferNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Offer with uniqueID: {id} not found";

    public OfferNotFoundException(UUID uniqueID) {
        super(MESSAGE.replace("{id}", uniqueID.toString()));
    }
}
