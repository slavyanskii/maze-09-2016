package ru.mail.park.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mail.park.Application;

/**
 * Created by viacheslav on 03.10.16.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,})
    @ResponseBody
    public ResponseEntity handleValidationException(Exception e) {
        Application.logger.warn(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ErrorResponse.VALIDATION_ERROR_MSG));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleGlobalException(Exception e) {
        Application.logger.warn(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ErrorResponse.SERVER_ERROR_MSG));
    }
}
