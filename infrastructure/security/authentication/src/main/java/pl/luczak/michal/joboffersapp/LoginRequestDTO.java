package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record LoginRequestDTO(
        @NotBlank(message = "{not.blank}")
        @NotNull(message = "{not.null}")
        String username,
        @NotBlank(message = "{not.blank}")
        @NotNull(message = "{not.null}")
        String password
) { }
