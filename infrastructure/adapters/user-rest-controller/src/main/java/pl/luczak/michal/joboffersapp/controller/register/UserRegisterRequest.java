package pl.luczak.michal.joboffersapp.controller.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record UserRegisterRequest(
        @NotBlank
        @NotNull
        String username,

        @NotBlank
        @NotNull
        String password
) {
}
