package com.tomasdev.akhanta.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail resourceNotFoundException(RuntimeException exception) {
        log.error("{} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({ServiceException.class, WrongCredentialsException.class, UserExistsException.class})
    public ProblemDetail badRequestException(RuntimeException exception) {
        log.error("{} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        log.error("{} - {}", ex.getClass().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDTO(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidContentTypeException.class})
    public ProblemDetail multipartMissingException(InvalidContentTypeException exception) {
        log.error("{} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "file missing. Check entity attached.");
    }

    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class, JWTVerificationException.class})
    public ProblemDetail unauthorizedException(RuntimeException exception) {
        log.error("{} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ProblemDetail accessDeniedException(RuntimeException exception) {
        log.error("{} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}