package com.tomasdev.akhanta.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, ServiceException.class, WrongCredentialsException.class, UserExistsException.class})
    public ProblemDetail badRequestException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler({InvalidContentTypeException.class})
    public ProblemDetail multipartMissingException(InvalidContentTypeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "multipart/form-data missing. Check entity attached.");
    }

    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class})
    public ProblemDetail unauthorizedException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({JWTVerificationException.class})
    public ProblemDetail jwtException(JWTVerificationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ProblemDetail accessDeniedException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}