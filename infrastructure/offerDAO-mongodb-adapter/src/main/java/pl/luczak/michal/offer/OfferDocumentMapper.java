package pl.luczak.michal.offer;

import org.springframework.stereotype.Service;
import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.UUID;
import java.util.function.Function;

@Service
final class OfferDocumentMapper implements Function<OfferDTO, OfferDocument> {

    static OfferDocument fromOfferDTO(OfferDTO offerDTO) {
        return mapFromOfferDTO(offerDTO);
    }

    @Override
    public OfferDocument apply(OfferDTO offerDTO) {
        return mapFromOfferDTO(offerDTO);
    }

    private static OfferDocument mapFromOfferDTO(OfferDTO offerDTO) {
        UUID uniqueID = offerDTO.uniqueID();
        if (offerDTO.isNew()) {
            uniqueID = UUID.randomUUID();
        }
        return OfferDocument.builder()
                .uniqueID(uniqueID.toString())
                .salary(offerDTO.salary())
                .url(offerDTO.url())
                .companyName(offerDTO.companyName())
                .jobName(offerDTO.jobName())
                .build();
    }
}
