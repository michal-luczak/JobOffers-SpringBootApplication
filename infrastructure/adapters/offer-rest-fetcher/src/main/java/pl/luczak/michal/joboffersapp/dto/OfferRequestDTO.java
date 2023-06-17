package pl.luczak.michal.joboffersapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import pl.luczak.michal.joboffersapp.offer.Fetchable;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

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
) implements Fetchable {
        @Override
        public OfferDTO toOfferDTO() {
                return OfferDTO.builder()
                        .uniqueID(null)
                        .companyName(companyName)
                        .salary(salary)
                        .url(url)
                        .jobName(jobName)
                        .build();
        }
}
