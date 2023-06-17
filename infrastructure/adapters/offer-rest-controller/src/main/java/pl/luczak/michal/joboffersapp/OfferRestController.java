package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.OfferController;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/offer")
class OfferRestController implements OfferController<ResponseEntity<?>, OfferSaveRequest> {

    private final OfferService offerService;
    private OfferSaveRequestToOfferDTOMapper offerSaveRequestToOfferDTOMapper;

    @PostMapping
    @Override
    public ResponseEntity<UUID> saveOffer(@RequestBody @Valid OfferSaveRequest offerSaveRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(offerService.saveOffer(
                        offerSaveRequestToOfferDTOMapper.toOfferDTO(offerSaveRequest)
                ));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<OfferDTO>> findAllOffers() {
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/{uniqueID}")
    @Override
    public ResponseEntity<OfferDTO> findOfferById(@PathVariable @Valid UUID uniqueID) {
        return ResponseEntity.ok(offerService.findOfferById(uniqueID));
    }
}
