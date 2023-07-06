package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record UserRegisterRequest(
        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        String username,

        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        String password
) {
}
