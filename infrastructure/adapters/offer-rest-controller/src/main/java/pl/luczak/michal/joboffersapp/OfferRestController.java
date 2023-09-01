package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferControllerPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/offers")
@Log4j2
class OfferRestController implements OfferControllerPort<ResponseEntity<?>, OfferSaveRequest> {

    private final OfferService offerService;
    private final OfferSaveRequestToOfferDTOMapper offerSaveRequestToOfferDTOMapper;
    private final OfferServiceCacheableWrapper offerServiceCacheableWrapper;

    @PostMapping
    @Override
    public ResponseEntity<String> saveOffer(@RequestBody @Valid OfferSaveRequest offerSaveRequest) {
        log.warn("User is trying to save Offer: " + offerSaveRequest);
        UUID uuid = offerService.saveOffer(
                offerSaveRequestToOfferDTOMapper.toOfferDTO(offerSaveRequest)
        );
        log.info("Offer: {} has been successfully saved", offerSaveRequest);
        offerServiceCacheableWrapper.clearOffersCache();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uuid.toString());
    }

    @GetMapping
    @Override
    public ResponseEntity<List<OfferDTO>> findAllOffers() {
        log.warn("Trying to find all offers...");
        List<OfferDTO> cacheableOffers = offerServiceCacheableWrapper.getCacheableOffers();
        log.info("User has successfully got all offers");
        return ResponseEntity.ok(
                cacheableOffers
        );
    }

    @GetMapping("/{uniqueID}")
    @Override
    public ResponseEntity<OfferDTO> findOfferById(@PathVariable @Valid UUID uniqueID) {
        log.warn("Trying to find offer with id: {}...", uniqueID);
        OfferDTO cacheableOfferById = offerServiceCacheableWrapper.getCacheableOfferById(uniqueID);
        log.info("User has successfully get offer: {}", cacheableOfferById);
        return ResponseEntity.ok(
                cacheableOfferById
        );
    }
}
