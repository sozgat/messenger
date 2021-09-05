package com.armut.messenger.presentation.api.dto.blacklist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlackListAPIRequestDTO {

    @NotEmpty(message = "Blocked Username field is mandatory.")
    @JsonProperty(value = "blockedUsername")
    private String blockedUsername;
}
