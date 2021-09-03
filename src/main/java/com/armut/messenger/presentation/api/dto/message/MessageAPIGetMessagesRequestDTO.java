package com.armut.messenger.presentation.api.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageAPIGetMessagesRequestDTO {

    @JsonProperty(value = "username")
    private String username;
}
