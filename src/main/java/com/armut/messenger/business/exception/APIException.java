package com.armut.messenger.business.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;
    private Object requestData;

    public APIException(String message, HttpStatus httpStatus, Object requestData) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.requestData = requestData;
    }
}
