package com.armut.messenger.presentation.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public abstract class AbstractAPIResponseDTO {

    private String status;
    @JsonIgnore
    private int httpStatus;
    private String phrase;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date timestamp;

    public AbstractAPIResponseDTO(String status, HttpStatus httpStatus) {
        this.status = status;
        this.httpStatus= httpStatus.value();
        this.phrase = httpStatus.getReasonPhrase();
        this.timestamp = new Date();
    }
}
