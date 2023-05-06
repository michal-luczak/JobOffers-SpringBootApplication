package pl.luczak.michal.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.input.OfferController;
import pl.luczak.michal.ports.output.OfferService;

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
    public ResponseEntity<OfferDTO> findOfferById(@PathVariable UUID uniqueID) {
        System.out.println(999999999);
        return new ResponseEntity<>(
                offerService.findOfferById(uniqueID),
                HttpStatus.OK
        );
    }
}
