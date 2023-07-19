package pl.luczak.michal.joboffersapp.validation.controller.api;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import pl.luczak.michal.joboffersapp.LogHandlerMethodExec;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor
@Log4j2
class APIValidationErrorHandler {

    private final MessageSource messageSource;
    private static final String loggerName = "APIValidationErrorHandler";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @LogHandlerMethodExec(
            value = loggerName,
            handlerClazz = APIValidationErrorHandler.class,
            caughtException = MethodArgumentNotValidException.class
    )
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
    @LogHandlerMethodExec(
            value = loggerName,
            handlerClazz = APIValidationErrorHandler.class,
            caughtException = HttpMessageNotReadableException.class
    )
    ResponseEntity<String> handleHttpMessageNotReadableException(WebRequest request) {
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("invalid.format", null, request.getLocale()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @LogHandlerMethodExec(
            value = loggerName,
            handlerClazz = APIValidationErrorHandler.class,
            caughtException = MethodArgumentTypeMismatchException.class
    )
    ResponseEntity<String> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception,
            WebRequest request
    ) {
        Locale locale = request.getLocale();
        Object firstArg = exception.getValue();
        Object secondArg = Objects.requireNonNull(exception.getRequiredType())
                .getSimpleName();
        Object[] args = new Object[] {
                firstArg,
                secondArg
        };
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("invalid.type", args, locale));
    }
}
