package ru.mail.park.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by viacheslav on 03.10.16.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CastomException.class)             //castom exception with error and body for my needs
    public ResponseEntity handleGlobalException(CastomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)  //testing global ExceptionHandler for Bad Request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleGlobalException() {
        final ErrorMsg errorMsg = new ErrorMsg(HttpStatus.BAD_REQUEST.toString(),"Wrong data in Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

}
