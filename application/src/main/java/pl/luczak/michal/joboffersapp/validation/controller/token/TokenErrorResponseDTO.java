package pl.luczak.michal.joboffersapp.validation.controller.token;

import org.springframework.http.HttpStatus;

record TokenErrorResponseDTO(
        String username,
        HttpStatus httpStatus
) {}
