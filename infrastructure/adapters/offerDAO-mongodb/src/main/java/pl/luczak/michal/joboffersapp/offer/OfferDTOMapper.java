package pl.luczak.michal.joboffersapp.offer;

import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.UUID;
import java.util.function.Function;

final class OfferDTOMapper implements IOfferDTOMapper<OfferDocument>, Function<OfferDocument, OfferDTO> {

    @Override
    public OfferDocument fromOfferDTO(OfferDTO offerDTO) {
        UUID uniqueID = offerDTO.uniqueID();
        if (uniqueID == null) {
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

    @Override
    public OfferDTO apply(OfferDocument offerDocument) {
        UUID uniqueID = null;
        if (offerDocument.uniqueID() != null) {
             uniqueID = UUID.fromString(offerDocument.uniqueID());
        }
        return OfferDTO.builder()
                .uniqueID(uniqueID)
                .salary(offerDocument.salary())
                .url(offerDocument.url())
                .companyName(offerDocument.companyName())
                .jobName(offerDocument.jobName())
                .build();
    }
}
