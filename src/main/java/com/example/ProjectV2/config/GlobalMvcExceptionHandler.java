package com.example.ProjectV2.config;

import com.example.ProjectV2.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalMvcExceptionHandler {


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(value = CustomizedIllegalArgumentException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomizedIllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = ImageException.class)
    public ResponseEntity<Object> handleNotFoundException(ImageException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }


    @ExceptionHandler(value = PermissionDeniedException.class)
    public ResponseEntity<Object> handleNotFoundException(PermissionDeniedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = NotEnoughAmountException.class)
    public ResponseEntity<Object> handleNotFoundException(NotEnoughAmountException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = TransactionException.class)
    public ResponseEntity<Object> handleNotFoundException(TransactionException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = NotUniqueException.class)
    public ResponseEntity<Object> handleNotFoundException(NotUniqueException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = UnAvailableServiceException.class)
    public ResponseEntity<Object> handleNotFoundException(UnAvailableServiceException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }




}
