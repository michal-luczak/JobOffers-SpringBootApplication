package pl.luczak.michal.controller.error;

import org.springframework.http.HttpStatus;

record OfferNotFoundResponse(
        String message,
        HttpStatus status
) {
}
