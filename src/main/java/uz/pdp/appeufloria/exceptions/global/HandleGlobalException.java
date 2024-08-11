package uz.pdp.appeufloria.exceptions.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.appeufloria.exceptions.NotFoundException;
import uz.pdp.appeufloria.payload.ApiResultDTO;

@RestControllerAdvice
public class HandleGlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ApiResultDTO.error(e));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handle(NotFoundException e) {
        return ResponseEntity.badRequest().body(ApiResultDTO.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        return ResponseEntity.badRequest().body(ApiResultDTO.error(e.getMessage()));
    }
}
