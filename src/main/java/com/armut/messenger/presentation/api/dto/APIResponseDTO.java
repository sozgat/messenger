package com.armut.messenger.presentation.api.dto;

import org.springframework.http.HttpStatus;

public class APIResponseDTO <T>extends AbstractAPIResponseDTO {

    private T data;

    public APIResponseDTO(HttpStatus httpStatus, T data) {
        super(httpStatus);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
