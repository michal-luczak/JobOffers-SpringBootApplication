package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
class TokenControllerErrorHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    BadCredentialsResponseDTO handleAuthenticationException(AuthenticationException exception) {
        return new BadCredentialsResponseDTO(Collections.singletonList(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    BadCredentialsResponseDTO handleInternalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        return new BadCredentialsResponseDTO(Collections.singletonList(exception.getMessage()));
    }
}
