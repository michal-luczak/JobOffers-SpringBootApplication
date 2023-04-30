package pl.luczak.michal.offer;

import org.springframework.stereotype.Service;
import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.UUID;
import java.util.function.Function;

@Service
final class OfferDTOMapper implements Function<OfferDocument, OfferDTO> {

    @Override
    public OfferDTO apply(OfferDocument offerDocument) {
        return OfferDTO.builder()
                .uniqueID(UUID.fromString(offerDocument.uniqueID()))
                .salary(offerDocument.salary())
                .url(offerDocument.url())
                .companyName(offerDocument.companyName())
                .jobName(offerDocument.jobName())
                .build();
    }
}
