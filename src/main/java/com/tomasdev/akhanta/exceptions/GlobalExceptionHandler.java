package com.tomasdev.akhanta.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.ServletException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, ServiceException.class, EmailValidationException.class, UserExistsException.class, PasswordIncorrectException.class})
    public ProblemDetail badRequestException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class, JWTVerificationException.class})
    public ProblemDetail unauthorizedException(AuthenticationException authenticationException) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail accessDeniedException(AccessDeniedException accessDeniedException) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, accessDeniedException.getMessage());
    }

}