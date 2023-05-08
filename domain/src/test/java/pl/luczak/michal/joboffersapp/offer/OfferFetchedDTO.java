package pl.luczak.michal.joboffersapp.offer;

import lombok.Builder;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

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
