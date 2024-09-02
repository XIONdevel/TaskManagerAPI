package org.noix.api.manager.exceptions.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.noix.api.manager.exceptions.InvalidDataException;
import org.noix.api.manager.exceptions.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NoPermissionException;

//TODO: add handling
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        ErrorResponse response = new ErrorResponse("Data is not valid", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ErrorResponse> handleNoPermissionException(NoPermissionException ex) {
        ErrorResponse response = new ErrorResponse(
                "You don`t have permission to perform this operation", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        ErrorResponse response = new ErrorResponse("Jwt token is not valid", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }










}
