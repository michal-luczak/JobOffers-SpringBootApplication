package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@ControllerAdvice
class APIValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    APIValidationErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(
                        Objects.requireNonNull(error.getDefaultMessage()),
                        error.getField())
                )
                .toList();
        return new APIValidationErrorDTO(errorMessages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    APIValidationErrorDTO handleHttpMessageNotReadableException() {
        return new APIValidationErrorDTO(Collections.singletonList("Unreadable request content. Please use JSON format"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    APIValidationErrorDTO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return new APIValidationErrorDTO(
                Collections.singletonList(
                        String.format(
                                "Failed to convert input: %s to %s",
                                exception.getValue(),
                                Objects.requireNonNull(exception.getRequiredType()).getSimpleName()
                        )
                )
        );
    }
}
