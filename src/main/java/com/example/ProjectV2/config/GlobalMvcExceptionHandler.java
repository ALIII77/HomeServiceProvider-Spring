package com.example.ProjectV2.config;

import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import com.example.ProjectV2.exception.ImageException;
import com.example.ProjectV2.exception.NotFoundException;
import com.example.ProjectV2.exception.PermissionDeniedException;
import com.sun.jdi.request.ExceptionRequest;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalMvcExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(value = CustomizedIllegalArgumentException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomizedIllegalArgumentException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(value = ImageException.class)
    public ResponseEntity<Object> handleNotFoundException(ImageException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }


    @ExceptionHandler(value = PermissionDeniedException.class)
    public ResponseEntity<Object> handleNotFoundException(PermissionDeniedException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }





}
