package com.mavericsystems.userservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomCreateUserException.class)
    ResponseEntity<ApiError> customerNotFoundHandler(Exception exception, ServletWebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getLocalizedMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        {   List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            List<String> errors = fieldErrors.stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiError apiError = new ApiError();
            apiError.setCode(String.valueOf(status.value()));
            apiError.setMessage(String.valueOf(errors));
            return new ResponseEntity<>(apiError, headers, HttpStatus.BAD_REQUEST);
        }

    }



}
