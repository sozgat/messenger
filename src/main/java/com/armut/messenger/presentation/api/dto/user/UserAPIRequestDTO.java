package com.armut.messenger.presentation.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserAPIRequestDTO {

    @NotEmpty(message = "Username field is mandatory.")
    @JsonProperty(value = "username")
    private String username;
    @NotEmpty(message = "Password field is mandatory.")
    @JsonProperty(value = "password")
    private String password;
}
