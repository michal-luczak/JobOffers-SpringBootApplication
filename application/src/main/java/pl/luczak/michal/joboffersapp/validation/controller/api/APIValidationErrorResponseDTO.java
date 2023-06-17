package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.springframework.http.HttpStatus;

import java.util.List;

public record APIValidationErrorResponseDTO(
        List<String> messages,
        HttpStatus status
) {
}
