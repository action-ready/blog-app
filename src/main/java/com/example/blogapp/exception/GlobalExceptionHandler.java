package com.example.blogapp.exception;


import com.example.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  {


    // handle specific exception
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotfoundException exception,
                                                                        WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception,
                                                                        WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // global exception

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
                                                                 ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
          String fieldName =   ((FieldError)error).getField();
          String message = error.getDefaultMessage();
          errors.put(fieldName,message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                        WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }


}
