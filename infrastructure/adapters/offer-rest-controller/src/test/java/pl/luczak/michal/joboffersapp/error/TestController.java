package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.OfferSaveRequest;
import pl.luczak.michal.joboffersapp.offer.OfferAlreadyExistsException;
import pl.luczak.michal.joboffersapp.offer.OfferNotFoundException;

import java.util.UUID;

@RestController
class TestController {

    @PostMapping("/test-exception")
    OfferErrorResponseDTO testEndpointToTestValidationHandler() {
        throw new OfferAlreadyExistsException("Test response message");
    }

    @GetMapping("/test-exception/{testParam}")
    ResponseEntity<String> testEndpointToTestMethodTypeException(@PathVariable UUID testParam) {
        throw new OfferNotFoundException(testParam);
    }
}
