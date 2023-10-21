package datum.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExceptionApp.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> exception(ExceptionApp exceptionApp) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(exceptionApp.getCode())
                        .message(exceptionApp.getMessage())
                        .build(),
                HttpStatus.valueOf(exceptionApp.getCode())
        );
    }
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<ErrorResponse> exception(Exception exception) {
//        return new ResponseEntity<>(
//                ErrorResponse.builder()
//                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .message("Внутренняя ошибка сервера")
//                        .build(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }
}
