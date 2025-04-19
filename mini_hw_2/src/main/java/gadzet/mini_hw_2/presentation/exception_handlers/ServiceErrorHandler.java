package gadzet.mini_hw_2.presentation.exception_handlers;

import gadzet.mini_hw_2.app.errors.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceErrorHandler {
    @ExceptionHandler(ServiceError.class)
    public ResponseEntity<Object> handleServiceError(ServiceError ex) {
        HttpStatus status;
        switch (ex.code) {
        case 400:
            status = HttpStatus.BAD_REQUEST;
            break;
        case 404:
            status = HttpStatus.NOT_FOUND;
            break;
        case 403:
            status = HttpStatus.FORBIDDEN;
            break;
        case 401:
            status = HttpStatus.UNAUTHORIZED;
            break;
        default:
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}