package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.offer.OfferAlreadyExistsException;
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
    OfferAlreadyExistsResponse handle(OfferAlreadyExistsException exception) {
        return new OfferAlreadyExistsResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
