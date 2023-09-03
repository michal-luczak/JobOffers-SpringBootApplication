package pl.luczak.michal.joboffersapp.validation.controller.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

record TestRequest(
        @NotNull(message = "testMessage")
        @Pattern(
                regexp = "https:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
                message = "wrongPattern"
        )
        @Size(min = 6, max = 10, message = "wrongSize")
        String testValue
) {
}
