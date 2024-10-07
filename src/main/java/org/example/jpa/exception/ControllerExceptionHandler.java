package org.example.jpa.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

// @ControllerAdvice surrounds the Controllers to apply some common logic (as an Exception Resolver)
// @ExceptionHandler is used across controllers to capture and respond to exceptions
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    // handle/trap errors produced by @GetMapping methods (e.g. @PathVariable receives an invalid value or id)
    @ExceptionHandler (ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex){

        Map<String, String> response = new HashMap<>();

        response.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest request){
        MessageNotReadableException msgNotReadable = new MessageNotReadableException();
        Map<String,String> response = new HashMap<>();
        response.put("error",msgNotReadable.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest request){

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        Collections.sort(errors);   // formats the listed errors by alphabetical order
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // handle/trap errors produced by @RequestBody annotation binding (e.g. no data sent in the request's body)


}