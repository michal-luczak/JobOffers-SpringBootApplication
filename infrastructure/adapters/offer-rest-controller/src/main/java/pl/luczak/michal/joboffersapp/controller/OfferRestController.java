package pl.luczak.michal.joboffersapp.controller;

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
        return new ResponseEntity<>(
                offerService.saveOffer(
                        offerSaveRequestToOfferDTOMapper.toOfferDTO(offerSaveRequest)
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Override
    public ResponseEntity<List<OfferDTO>> findAllOffers() {
        return new ResponseEntity<>(
                offerService.findAllOffers(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uniqueID}")
    @Override
    public ResponseEntity<OfferDTO> findOfferById(@PathVariable @Valid UUID uniqueID) {
        return new ResponseEntity<>(
                offerService.findOfferById(uniqueID),
                HttpStatus.OK
        );
    }
}
