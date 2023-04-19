package pl.luczak.michal.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.offer.dto.OfferRequestDTO;
import pl.luczak.michal.ports.OfferDAO;
import pl.luczak.michal.ports.OfferFetcherPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

    private final OfferDAO offerDAO;
    private final OfferFetcherPort offerFetcherPort;

    public List<OfferDTO> findAllOffers() {
        return offerDAO.findAllOffers();
    }

    public UUID saveOffer(OfferDTO offerDTO) {
        return offerDAO.saveOffer(offerDTO);
    }

    public OfferDTO findOfferById(UUID uniqueID) {
        return offerDAO.findOfferById(uniqueID)
                .orElseThrow(() -> new OfferNotFoundException(uniqueID));
    }

    public List<OfferDTO> fetchAllOffersAndSaveAllIfNotExists() {
        List<OfferRequestDTO> fetchedOffers = offerFetcherPort.fetchOffers();
        return fetchedOffers.stream()
                .filter(requestDTO -> offerDAO.findOfferByUrl(requestDTO.url()).isEmpty())
                .map(requestDTO -> OfferDTO.builder()
                        .companyName(requestDTO.companyName())
                        .salary(requestDTO.salary())
                        .jobName(requestDTO.jobName())
                        .url(requestDTO.url())
                        .build()
                ).toList();
    }
}

