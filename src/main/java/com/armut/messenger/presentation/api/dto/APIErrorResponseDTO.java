package com.armut.messenger.presentation.api.dto;

import org.springframework.http.HttpStatus;

public class APIErrorResponseDTO <T>extends AbstractAPIResponseDTO {

    private T error;

    public APIErrorResponseDTO(HttpStatus httpStatus, T error) {
        super(httpStatus);
        this.error = error;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }
}