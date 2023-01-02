package spring.advanced.advancedspring.app.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.advanced.advancedspring.trace.TraceStatus;
import spring.advanced.advancedspring.trace.hellotrace.HelloTraceV1;

@RestControllerAdvice
@RequiredArgsConstructor
public class OrderExController {


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> OrderExHandler(IllegalStateException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
