package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;

record UserAlreadyExistsResponse(
        String message,
        HttpStatus status
) {
}
