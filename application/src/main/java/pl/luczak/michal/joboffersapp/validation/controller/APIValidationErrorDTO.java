package pl.luczak.michal.joboffersapp.validation.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

public record APIValidationErrorDTO(
        List<String> messages,
        HttpStatus status
) {
}
