package pl.luczak.michal.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.luczak.michal.offer.OfferNotFoundException;

@ControllerAdvice
class OfferRestControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    OfferNotFoundResponse handle(OfferNotFoundException offerNotFoundException) {
        return new OfferNotFoundResponse(
                offerNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
