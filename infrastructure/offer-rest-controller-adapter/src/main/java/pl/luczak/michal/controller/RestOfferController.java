package pl.luczak.michal.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.offer.OfferController;
import pl.luczak.michal.ports.output.OfferService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/offer")
class RestOfferController implements OfferController<ResponseEntity<?>> {

    private final OfferService offerService;

    @PostMapping
    @Override
    public ResponseEntity<UUID> saveOffer(@RequestBody OfferDTO offerDTO) {
        return new ResponseEntity<>(
                offerService.saveOffer(offerDTO),
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
        return new ResponseEntity<>(
                offerService.findOfferById(uniqueID),
                HttpStatus.OK
        );
    }
}
