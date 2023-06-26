package pl.luczak.michal.joboffersapp;

import org.springframework.stereotype.Service;
import pl.luczak.michal.joboffersapp.offer.IOfferDTOMapper;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.UUID;

@Service
class OfferSaveRequestToOfferDTOMapper implements IOfferDTOMapper<OfferSaveRequest> {

    @Override
    public OfferSaveRequest fromOfferDTO(OfferDTO offerDTO) {
        return OfferSaveRequest.builder()
                .url(offerDTO.url())
                .salary(offerDTO.salary())
                .jobName(offerDTO.jobName())
                .companyName(offerDTO.companyName())
                .build();
    }

    @Override
    public OfferDTO apply(OfferSaveRequest offerSaveRequest) {
        return OfferDTO.builder()
                .uniqueID(null)
                .companyName(offerSaveRequest.companyName())
                .jobName(offerSaveRequest.jobName())
                .salary(offerSaveRequest.salary())
                .url(offerSaveRequest.url())
                .build();
    }
}
