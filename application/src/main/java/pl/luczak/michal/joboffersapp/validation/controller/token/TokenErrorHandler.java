package pl.luczak.michal.joboffersapp.validation.controller.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.luczak.michal.joboffersapp.loginandsignup.UserNotFoundException;

@ControllerAdvice
class TokenErrorHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    TokenErrorResponseDTO handle(UserNotFoundException exception) {
        return new TokenErrorResponseDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
