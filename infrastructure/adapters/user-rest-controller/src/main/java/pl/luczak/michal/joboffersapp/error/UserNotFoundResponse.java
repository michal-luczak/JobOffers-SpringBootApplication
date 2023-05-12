package pl.luczak.michal.joboffersapp.error;

import org.springframework.http.HttpStatus;

record UserNotFoundResponse(
        String message,
        HttpStatus status
) {
}
