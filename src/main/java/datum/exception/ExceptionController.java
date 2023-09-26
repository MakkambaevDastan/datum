package datum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Object> badRequestException(BadRequest e) {
        return new ResponseEntity<>(
                ExceptionMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<Object> entityNotFound(EntityNotFound e) {
        return new ResponseEntity<>(
                ExceptionMessage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Object> internalServerError(InternalServerError e) {
        return new ResponseEntity<>(
                ExceptionMessage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<Object> mailException(MailException e) {
        return new ResponseEntity<>(
                ExceptionMessage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}
