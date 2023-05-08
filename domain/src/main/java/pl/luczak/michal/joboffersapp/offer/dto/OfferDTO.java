package pl.luczak.michal.joboffersapp.offer.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OfferDTO(
        UUID uniqueID,
        String url,
        String companyName,
        String jobName,
        String salary
) {

    public boolean isNew() {
        return uniqueID == null;
    }
}
