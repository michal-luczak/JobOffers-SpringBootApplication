package pl.luczak.michal.controller.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record APIValidationErrorDTO(
        List<String> messages,
        HttpStatus status
) {
}
