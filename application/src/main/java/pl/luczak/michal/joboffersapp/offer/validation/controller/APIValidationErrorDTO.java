package pl.luczak.michal.joboffersapp.offer.validation.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

public record APIValidationErrorDTO(
        List<String> messages,
        HttpStatus status
) {
}
