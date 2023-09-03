package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

record UserRegisterRequest(
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @Size(min = 5, max = 25, message = "{wrong.size}")
        String username,

        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @Size(min = 8, max = 24, message = "{wrong.size}")
        String password
) {
}
