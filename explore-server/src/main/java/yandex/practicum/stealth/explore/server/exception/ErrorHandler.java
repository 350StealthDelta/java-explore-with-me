package yandex.practicum.stealth.explore.server.exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse generalException(Throwable e) {
        String reason = "Unexpected internal server error.";
        log.warn("Internal server error {}, {}, {}, {}", e.getMessage(), reason, HttpStatus.INTERNAL_SERVER_ERROR.name(),
                LocalDateTime.now());
        e.printStackTrace();
        return new ErrorResponse(e.getStackTrace(), e.getMessage(), reason,
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
    }

    @ExceptionHandler({ValidationException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            DuplicateKeyException.class,
            RequestWithEnErrorException.class,
            MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(Exception e) {
        String reason = "For the requested operation the conditions are not met.";
        log.warn("IllegalArgumentException {}, {}, {}, {}", e.getMessage(), reason, HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
        e.printStackTrace();
        return new ErrorResponse(e.getStackTrace(), e.getMessage(), reason,
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler({NullPointerException.class,
            CustomEntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFoundException(Exception e) {
        String reason = "The required object was not found.";
        log.warn("{}, {}, {}, {}", HttpStatus.NOT_FOUND.name(), reason, e.getMessage(),
                LocalDateTime.now());
        e.printStackTrace();
        return new ErrorResponse(e.getStackTrace(), e.getMessage(), reason, HttpStatus.NOT_FOUND,
                LocalDateTime.now());
    }

    @ExceptionHandler({NotAllowedException.class,
            ConditionsNotMetException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse notAllowedException(NotAllowedException e) {
        String reason = "For the requested operation the conditions are not met.";
        log.warn("{}, {}, {}, {}", HttpStatus.FORBIDDEN.name(), reason, e.getMessage(),
                LocalDateTime.now());
        e.printStackTrace();
        return new ErrorResponse(e.getStackTrace(), e.getMessage(), reason, HttpStatus.FORBIDDEN,
                LocalDateTime.now());
    }

    @ExceptionHandler({HttpClientErrorException.Conflict.class,
            DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conditionsNotMetException(Exception e) {
        String reason = "For the requested operation the conditions are not met.";
        log.warn("{}, {}, {}, {}", HttpStatus.CONFLICT.name(), reason, e.getMessage(),
                LocalDateTime.now());
        e.printStackTrace();
        return new ErrorResponse(e.getStackTrace(), e.getMessage(), reason, HttpStatus.CONFLICT,
                LocalDateTime.now());
    }
}
