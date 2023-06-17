package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;

record OfferAlreadyExistsResponse(
        String message,
        HttpStatus status
) {
}
