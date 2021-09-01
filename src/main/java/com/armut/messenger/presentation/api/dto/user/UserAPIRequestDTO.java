package com.armut.messenger.presentation.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAPIRequestDTO {

    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "password")
    private String password;
}
