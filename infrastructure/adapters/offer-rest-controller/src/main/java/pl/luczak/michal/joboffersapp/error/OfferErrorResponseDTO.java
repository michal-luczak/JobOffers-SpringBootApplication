package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;

record OfferErrorResponseDTO(
        String message,
        HttpStatus status
) {
}
