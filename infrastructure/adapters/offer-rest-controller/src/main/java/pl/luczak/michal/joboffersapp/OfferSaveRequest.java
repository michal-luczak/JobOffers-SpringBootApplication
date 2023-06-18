package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record OfferSaveRequest(
        @NotNull(message = "{message.test1}")
        @NotBlank
        @JsonProperty("offerUrl")
        String url,
        @NotNull
        @NotBlank
        @JsonProperty("company")
        String companyName,
        @NotNull
        @NotBlank
        @JsonProperty("title")
        String jobName,
        @NotNull
        @NotBlank
        @JsonProperty("salary")
        String salary
) { }
