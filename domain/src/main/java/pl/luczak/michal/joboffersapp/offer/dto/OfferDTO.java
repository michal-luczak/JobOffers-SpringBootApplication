package pl.luczak.michal.joboffersapp.offer.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
public record OfferDTO(
        UUID uniqueID,
        String url,
        String companyName,
        String jobName,
        String salary
) implements Serializable {}
