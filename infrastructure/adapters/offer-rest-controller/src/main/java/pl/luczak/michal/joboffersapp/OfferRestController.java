package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
class OfferRestController implements OfferControllerPort<ResponseEntity<?>, OfferSaveRequest> {

    private final OfferService offerService;
    private final OfferSaveRequestToOfferDTOMapper offerSaveRequestToOfferDTOMapper;
    private final OfferServiceCacheableWrapper offerServiceCacheableWrapper;

    @PostMapping
    @Override
    public ResponseEntity<String> saveOffer(@RequestBody @Valid OfferSaveRequest offerSaveRequest) {
        UUID uuid = offerService.saveOffer(
                offerSaveRequestToOfferDTOMapper.toOfferDTO(offerSaveRequest)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid.toString());
    }

    @GetMapping
    @Override
    public ResponseEntity<List<OfferDTO>> findAllOffers() {
        return ResponseEntity.ok(offerServiceCacheableWrapper.getCacheableOffers());
    }

    @GetMapping("/{uniqueID}")
    @Override
    public ResponseEntity<OfferDTO> findOfferById(@PathVariable @Valid UUID uniqueID) {
        return ResponseEntity.ok(offerServiceCacheableWrapper.getCacheableOffer(uniqueID));
    }
}
