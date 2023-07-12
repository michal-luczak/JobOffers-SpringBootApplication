package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.context.annotation.PropertySource;

@Builder
@PropertySource("classpath:validationMessages.properties")
public record OfferSaveRequest(
        @NotNull(message = "{not.null}")
        @NotBlank(message = "{not.blank}")
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
