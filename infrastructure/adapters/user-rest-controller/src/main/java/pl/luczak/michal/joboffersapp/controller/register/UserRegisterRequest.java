package pl.luczak.michal.joboffersapp.controller.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record UserRegisterRequest(
        @NotBlank(message = "{not.blank}")
        @NotNull(message = "{not.null}")
        String username,

        @NotBlank(message = "{not.blank}")
        @NotNull(message = "{not.null}")
        String password
) {
}
