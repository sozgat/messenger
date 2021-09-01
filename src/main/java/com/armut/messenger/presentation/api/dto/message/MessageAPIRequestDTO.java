package com.armut.messenger.presentation.api.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageAPIRequestDTO {

    @JsonProperty(value = "fromUsername")
    private String fromUsername;
    @JsonProperty(value = "toUsername")
    private String toUsername;
    @JsonProperty(value = "yourMessage")
    private String yourMessage;
}
