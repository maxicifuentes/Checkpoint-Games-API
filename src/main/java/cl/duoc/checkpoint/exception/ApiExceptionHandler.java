package cl.duoc.checkpoint.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, String>> invalid(IllegalArgumentException ex) { return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage())); }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Map<String, String>> state(IllegalStateException ex) { return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", ex.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getFieldErrors().stream().findFirst().map(e -> e.getField() + ": " + e.getDefaultMessage()).orElse("Datos inválidos");
        return ResponseEntity.badRequest().body(Map.of("mensaje", error));
    }
}
