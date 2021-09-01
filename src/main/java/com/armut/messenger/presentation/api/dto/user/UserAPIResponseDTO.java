package com.armut.messenger.presentation.api.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAPIResponseDTO  {
    private String username;
    private String token;
    private LocalDateTime tokenExpiryDate;
}
