package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;

record OfferNotFoundResponse(
        String message,
        HttpStatus status
) {
}
