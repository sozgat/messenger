package com.armut.messenger.presentation.api.dto.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageAPIResponseDTO {

    private String fromUsername;
    private String toUsername;
    private String yourMessage;
    private LocalDateTime messageCreatedTime;
}
