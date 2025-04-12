package pl.luczak.michal.joboffersapp.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferDAOPort;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Log4j2
class OfferDAOMongoAdapter implements OfferDAOPort {

    private final OfferRepository offerRepository;
    private final IOfferDTOMapper<OfferDocument> offerDTOMapper;

    @Override
    public UUID saveOffer(OfferDTO offerDTO) {
        OfferDocument offerDocument = offerDTOMapper.fromOfferDTO(offerDTO);
        String uniqueID;
        try {
            log.warn("Trying to save offer: {}", offerDTO);
            uniqueID = offerRepository.save(offerDocument).uniqueID();
        } catch (DuplicateKeyException exception) {
            log.error("Offer: {} is already exists", offerDTO);
            throw new OfferAlreadyExistsException(exception.getMessage());
        }
        return UUID.fromString(uniqueID);
    }

    @Override
    public UUID deleteOffer(OfferDTO offerDTO) {
        OfferDocument offerDocument = offerDTOMapper.fromOfferDTO(offerDTO);
        log.warn("Trying to delete offer: {}", offerDTO);
        offerRepository.delete(offerDocument);
        log.info("Successfully deleted offer: {}", offerDTO);
        return UUID.fromString(offerDocument.uniqueID());
    }

    @Override
    public Optional<OfferDTO> findOfferById(UUID uniqueID) {
        log.warn("Trying to find offer with UUID: {}...", uniqueID);
        Optional<OfferDTO> offerDTO = offerRepository.findById(uniqueID.toString())
                .map(offerDTOMapper);
        log.info("Successfully found offer with uniqueID: {}", uniqueID);
        return offerDTO;
    }

    @Override
    public List<OfferDTO> findAllOffers() {
        log.warn("Trying to find all offers...");
        return offerRepository.findAll()
                .stream()
                .map(offerDTOMapper)
                .toList();
    }

    @Override
    public List<UUID> saveAllOffers(List<OfferDTO> offerDTOs) {
        List<UUID> uniqueIDs = new LinkedList<>();
        offerDTOs.forEach(offerDTO -> uniqueIDs.add(saveOffer(offerDTO)));
        return uniqueIDs;
    }

    @Override
    public Optional<OfferDTO> findOfferByUrl(String url) {
        log.warn("Trying to find offer with url: {}...", url);
        Optional<OfferDTO> offerDTO = offerRepository.findByUrl(url)
                .map(offerDTOMapper);
        log.info("Successfully find offer with url: {}", url);
        return offerDTO;
    }
}
