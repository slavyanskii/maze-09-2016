package ru.mail.park.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by viacheslav on 03.10.16.
 */
public class CustomException extends Exception {
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}