package com.armut.messenger.presentation.api.dto.blacklist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlackListAPIRequestDTO {
    @JsonProperty(value = "blockingUsername")
    private String blockingUsername;
    @JsonProperty(value = "blockedUsername")
    private String blockedUsername;
}
