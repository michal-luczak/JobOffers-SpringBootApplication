package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record OfferSaveRequest(
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @Pattern(
                regexp = "https://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)",
                message = "{wrong.link.pattern}"
        )
        @JsonProperty("offerUrl")
        String url,
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @JsonProperty("title")
        String jobName,
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @JsonProperty("company")
        String companyName,
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
        @JsonProperty("salary")
        String salary
) { }
