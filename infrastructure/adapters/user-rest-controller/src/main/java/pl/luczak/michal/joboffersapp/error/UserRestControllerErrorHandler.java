package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.luczak.michal.joboffersapp.LogHandlerMethodExec;
import pl.luczak.michal.joboffersapp.loginandsignup.UserIdDuplicationException;

@ControllerAdvice
class UserRestControllerErrorHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @LogHandlerMethodExec(
            value = "UserRestControllerErrorHandler",
            handlerClazz = UserRestControllerErrorHandler.class,
            caughtException = UserIdDuplicationException.class
    )
    UserAlreadyExistsResponse handle(UserIdDuplicationException exception) {
        return new UserAlreadyExistsResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
