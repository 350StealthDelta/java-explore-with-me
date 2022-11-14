package yandex.practicum.stealth.explore.stat.exeption;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse generalException(Throwable e) {
        log.warn("Неожиданная ошибка сервера. {}", e.getMessage());
        return new ErrorResponse("Неожиданная ошибка сервера.", e.getMessage());
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), "IllegalArgumentException");
    }
}
