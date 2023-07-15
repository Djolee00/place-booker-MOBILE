package com.placebooker.exception;

import com.placebooker.exception.custom.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse onMethodArgumentNotValidExceptionI(MethodArgumentNotValidException ex) {
    Set<Violation> violations = new HashSet<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      Violation violation =
          new Violation(
              fieldError.getField(), fieldError.getDefaultMessage(), OffsetDateTime.now());
      violations.add(violation);
    }

    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      Violation violation =
          new Violation(error.getCode(), error.getDefaultMessage(), OffsetDateTime.now());
      violations.add(violation);
    }

    return new ErrorResponse(violations);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomException> onNotFoundException(RuntimeException ex) {
    CustomException exception =
        new CustomException(ex.getMessage(), HttpStatus.NOT_FOUND, OffsetDateTime.now());
    return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomException> onIllegalArgumentException(RuntimeException ex) {
    CustomException exception =
        new CustomException(ex.getMessage(), HttpStatus.BAD_REQUEST, OffsetDateTime.now());
    return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
    Set<Violation> violations = new HashSet<>();

    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessage = violation.getMessage();
      violations.add(new Violation(fieldName, errorMessage, OffsetDateTime.now()));
    }

    return new ErrorResponse(violations);
  }
}

record CustomException(String message, HttpStatus http, OffsetDateTime timestamp) {}

record Violation(String field, String message, OffsetDateTime timestamp) {}

record ErrorResponse(Set<Violation> violations) {}
