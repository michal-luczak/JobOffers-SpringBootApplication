package pl.luczak.michal.joboffersapp.controller.error;

import org.springframework.http.HttpStatus;

record OfferPostErrorResponse(
    String message,
    HttpStatus status
) {
}
