package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        String username,
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        String password
) { }
