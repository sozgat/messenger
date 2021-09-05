package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIResponseDTO;

public class BlackListAPIMapper {

    public static BlackListAPIResponseDTO fromDomain(BlackList blackList){
        BlackListAPIResponseDTO blackListAPIResponseDTO = new BlackListAPIResponseDTO();

        blackListAPIResponseDTO.setBlockingUsername(blackList.getBlockingUserId().getUsername());
        blackListAPIResponseDTO.setBlockedUsername(blackList.getBlockedUserId().getUsername());

        return blackListAPIResponseDTO;
    }
}
