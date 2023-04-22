package pl.luczak.michal.offer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OfferRequestDTO(
        @JsonProperty("company")
        String companyName,

        @JsonProperty("title")
        String jobName,

        @JsonProperty("salary")
        String salary,

        @JsonProperty("offerUrl")
        String url
) {
}
