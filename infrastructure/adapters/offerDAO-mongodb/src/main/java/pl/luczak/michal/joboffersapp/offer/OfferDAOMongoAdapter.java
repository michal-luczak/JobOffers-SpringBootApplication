package pl.luczak.michal.joboffersapp.offer;

import lombok.AllArgsConstructor;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.OfferDAOPort;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
class OfferDAOMongoAdapter implements OfferDAOPort {

    private final OfferRepository offerRepository;
    private final IOfferDTOMapper<OfferDocument> offerDTOMapper;

    @Override
    public UUID saveOffer(OfferDTO offerDTO) {
        OfferDocument offerDocument = offerDTOMapper.fromOfferDTO(offerDTO);
        String uniqueID = offerRepository.save(offerDocument).uniqueID();
        return UUID.fromString(uniqueID);
    }

    @Override
    public UUID deleteOffer(OfferDTO offerDTO) {
        OfferDocument offerDocument = offerDTOMapper.fromOfferDTO(offerDTO);
        offerRepository.delete(offerDocument);
        return UUID.fromString(offerDocument.uniqueID());
    }

    @Override
    public Optional<OfferDTO> findOfferById(UUID uniqueID) {
        return offerRepository.findById(uniqueID.toString())
                .map(offerDTOMapper);
    }

    @Override
    public List<OfferDTO> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(offerDTOMapper)
                .toList();
    }

    @Override
    public List<UUID> saveAllOffers(List<OfferDTO> offerDTOs) {
        List<UUID> uniqueIDs = new LinkedList<>();
        offerDTOs.forEach(offerDTO -> {
            uniqueIDs.add(saveOffer(offerDTO));
        });
        return uniqueIDs;
    }

    @Override
    public Optional<OfferDTO> findOfferByUrl(String url) {
        return offerRepository.findByUrl(url)
                .map(offerDTOMapper);
    }
}
