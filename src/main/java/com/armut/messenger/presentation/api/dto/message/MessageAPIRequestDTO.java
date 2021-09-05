package com.armut.messenger.presentation.api.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MessageAPIRequestDTO {

    @NotEmpty(message = "Username field is mandatory.")
    @JsonProperty(value = "username")
    private String username;

    @NotEmpty(message = "Message field is mandatory.")
    @JsonProperty(value = "message")
    private String message;
}
