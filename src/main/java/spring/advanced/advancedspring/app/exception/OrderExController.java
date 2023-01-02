package spring.advanced.advancedspring.app.v0;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExControllerV0 {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> OrderExHandler(IllegalStateException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
