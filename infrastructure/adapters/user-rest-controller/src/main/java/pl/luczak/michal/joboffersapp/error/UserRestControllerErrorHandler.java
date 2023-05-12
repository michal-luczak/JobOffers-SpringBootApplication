package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.luczak.michal.joboffersapp.loginandsignup.UserAlreadyExistsException;
import pl.luczak.michal.joboffersapp.loginandsignup.UserNotFoundException;

@ControllerAdvice
class UserRestControllerErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    UserNotFoundResponse handle(UserNotFoundException exception) {
        return new UserNotFoundResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    UserAlreadyExistsResponse handle(UserAlreadyExistsException exception) {
        return new UserAlreadyExistsResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
