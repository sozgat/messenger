package com.armut.messenger.presentation.api.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MessageAPIGetMessagesRequestDTO {

    @NotEmpty(message = "Username field is mandatory.")
    @JsonProperty(value = "username")
    private String username;
}
