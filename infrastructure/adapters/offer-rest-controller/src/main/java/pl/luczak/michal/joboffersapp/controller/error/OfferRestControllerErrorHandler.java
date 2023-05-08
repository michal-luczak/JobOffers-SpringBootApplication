package pl.luczak.michal.joboffersapp.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import pl.luczak.michal.joboffersapp.offer.OfferNotFoundException;

@ControllerAdvice
class OfferRestControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    OfferNotFoundResponse handle(OfferNotFoundException exception) {
        return new OfferNotFoundResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    OfferPostErrorResponse handle(DuplicateKeyException exception) {
        return new OfferPostErrorResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
