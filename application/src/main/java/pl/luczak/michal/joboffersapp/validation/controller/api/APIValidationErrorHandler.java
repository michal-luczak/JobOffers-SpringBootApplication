package pl.luczak.michal.joboffersapp.validation.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
@AllArgsConstructor
class APIValidationErrorHandler {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    APIValidationErrorDTO handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ) {

        Locale locale = request.getLocale();
        Set<String> errorsFields = exception.getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.toSet());
        List<FieldErrorMessages> fieldErrorMessagesList = errorsFields.stream()
                .map(exception::getFieldErrors)
                .map(fieldErrors -> {
                    List<String> stringList = fieldErrors.stream()
                            .map(fieldError -> messageSource.getMessage(fieldError, locale))
                            .toList();
                    return new FieldErrorMessages(
                            fieldErrors.get(0).getField(), stringList
                    );
                }).toList();
        return new APIValidationErrorDTO(fieldErrorMessagesList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest()
                .body("Unreadable request content. Please use JSON format");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<String> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception,
            WebRequest request
    ) {
        Locale locale = request.getLocale();
        Object[] args = new Object[2];
        args[0] = exception.getValue();
        args[1] = Objects.requireNonNull(exception.getRequiredType())
                .getSimpleName();
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("invalid.type", args, locale));
    }
}
