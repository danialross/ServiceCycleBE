package com.danialross.ServiceCycle.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //when ResponseStatusException throws
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleException(ResponseStatusException ex){
        int code = ex.getStatusCode().value();
        ApiError responseBody = ApiError.builder()
                .statusCode(code)
                .messages(ex.getReason())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(code)
                .body(responseBody);
    }

    //for when @Valid throws if validation not met
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleStringValidation(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ApiError response = ApiError.builder()
                .statusCode(ex.getStatusCode().value())
                .messages(errors)
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiError response = ApiError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .messages(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
