package pl.luczak.michal.joboffersapp.offer;

public class OfferAlreadyExistsException extends RuntimeException {

    public OfferAlreadyExistsException(String message) {
        super(message);
    }
}
