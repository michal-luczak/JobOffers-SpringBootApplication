package pl.luczak.michal.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record OfferSaveRequest(
        @NotNull(message = "{message.test1}")
        @NotEmpty
        String url,
        @NotNull
        @NotEmpty
        String companyName,
        @NotNull
        @NotEmpty
        String jobName,
        @NotNull
        @NotEmpty
        String salary
) { }
