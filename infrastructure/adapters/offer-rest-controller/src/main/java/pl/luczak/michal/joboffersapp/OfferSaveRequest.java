package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferSaveRequest(
        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        @JsonProperty("offerUrl")
        String url,
        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        @JsonProperty("title")
        String jobName,
        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        @JsonProperty("company")
        String companyName,
        @NotNull(message = "Field %s must not be null")
        @NotBlank(message = "Field %s must not be blank")
        @JsonProperty("salary")
        String salary
) { }
