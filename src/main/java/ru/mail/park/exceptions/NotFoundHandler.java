package ru.mail.park.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.Application;

/**
 * Created by kirrok on 21.10.16.
 */

@RestController
public class NotFoundHandler {
    @RequestMapping("/**")
    public ResponseEntity handle404() {
        Application.logger.warn(HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ErrorResponse.NOT_FOUND_MSG));
    }
}