package pl.luczak.michal.joboffersapp.validation.controller.api;

import jakarta.validation.constraints.NotNull;

record TestRequest(
        @NotNull(message = "testMessage")
        Integer testValue
) {
}
