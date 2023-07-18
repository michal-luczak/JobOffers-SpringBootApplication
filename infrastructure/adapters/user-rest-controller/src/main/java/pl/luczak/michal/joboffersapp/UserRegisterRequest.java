package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record UserRegisterRequest(
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        String username,

        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        String password
) {
}
