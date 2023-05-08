package pl.luczak.michal.joboffersapp.controller.error;

import org.springframework.http.HttpStatus;

record OfferNotFoundResponse(
        String message,
        HttpStatus status
) {
}
