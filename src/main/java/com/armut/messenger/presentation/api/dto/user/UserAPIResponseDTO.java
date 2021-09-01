package com.armut.messenger.presentation.api.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserAPIResponseDTO  {
    private String username;
    private String token;
    private Date tokenExpiryDate;
}
