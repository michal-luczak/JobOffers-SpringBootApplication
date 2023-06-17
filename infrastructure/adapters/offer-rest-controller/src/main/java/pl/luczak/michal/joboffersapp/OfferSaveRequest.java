package pl.luczak.michal.joboffersapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record OfferSaveRequest(
        @NotNull(message = "{message.test1}")
        @NotBlank
        String url,
        @NotNull
        @NotBlank
        String companyName,
        @NotNull
        @NotBlank
        String jobName,
        @NotNull
        @NotBlank
        String salary
) { }
