package pl.luczak.michal.joboffersapp.validation.controller.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

record TestRequest(
        @NotNull(message = "testMessage")
        @Pattern(
                regexp = "https:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
                message = "wrongPattern"
        )
        String testValue
) {
}
