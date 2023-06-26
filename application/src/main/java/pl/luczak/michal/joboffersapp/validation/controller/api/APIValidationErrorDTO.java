package pl.luczak.michal.joboffersapp.validation.controller.api;

import java.util.List;

public record APIValidationErrorDTO(
        List<String> errors
) {}
