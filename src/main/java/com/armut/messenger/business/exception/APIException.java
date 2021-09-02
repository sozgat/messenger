package com.armut.messenger.business.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public APIException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
