package com.armut.messenger.presentation.api.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MessageAPIRequestDTO {

    @NotEmpty(message = "toUsername field is mandatory.")
    @JsonProperty(value = "toUsername")
    private String toUsername;

    @NotEmpty(message = "Message field is mandatory.")
    @JsonProperty(value = "yourMessage")
    private String yourMessage;
}
