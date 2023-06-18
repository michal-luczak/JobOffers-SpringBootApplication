package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;

record LoginRequestDTO(
        @NotBlank(message = "{username.not.blank}") String username,
        @NotBlank(message = "{password.not.blank}") String password
) { }
