package datum.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExceptionApp.class)
    public ResponseEntity<ExceptionResponse> exception(ExceptionApp exceptionApp) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .status(exceptionApp.getCode())
                        .message(exceptionApp.getMessage())
                        .build(),
                HttpStatus.valueOf(exceptionApp.getCode())
        );
    }
}
