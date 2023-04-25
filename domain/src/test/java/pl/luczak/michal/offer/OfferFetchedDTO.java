package pl.luczak.michal.offer;

import lombok.Builder;
import pl.luczak.michal.offer.dto.OfferDTO;

@Builder
record OfferFetchedDTO (
        String companyName,
        String jobName,
        String salary,
        String url
) implements Fetchable {

    @Override
    public OfferDTO toOfferDTO() {
        return OfferDTO.builder()
                .companyName(companyName)
                .salary(salary)
                .url(url)
                .jobName(jobName)
                .build();
    }
}
