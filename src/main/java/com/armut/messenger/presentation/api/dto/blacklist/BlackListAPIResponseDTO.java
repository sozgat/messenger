package com.armut.messenger.presentation.api.dto.blacklist;

import lombok.Data;

@Data
public class BlackListAPIResponseDTO {
    private String blockingUsername;
    private String blockedUsername;
}
