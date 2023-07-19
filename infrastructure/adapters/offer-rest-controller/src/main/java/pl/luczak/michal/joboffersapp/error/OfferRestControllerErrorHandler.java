package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.LogHandlerMethodExec;
import pl.luczak.michal.joboffersapp.offer.OfferAlreadyExistsException;
import pl.luczak.michal.joboffersapp.offer.OfferNotFoundException;

@ControllerAdvice
class OfferRestControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @LogHandlerMethodExec(
            value = "OfferRestControllerErrorHandler",
            handlerClazz = OfferRestControllerErrorHandler.class,
            caughtException = OfferNotFoundException.class
    )
    OfferErrorResponseDTO handle(OfferNotFoundException exception) {
        return new OfferErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(OfferAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @LogHandlerMethodExec(
            value = "OfferRestControllerErrorHandler",
            handlerClazz = OfferRestControllerErrorHandler.class,
            caughtException = OfferAlreadyExistsException.class
    )
    OfferErrorResponseDTO handle(OfferAlreadyExistsException exception) {
        return new OfferErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
