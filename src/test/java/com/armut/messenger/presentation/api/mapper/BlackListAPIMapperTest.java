package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIResponseDTO;
import org.junit.Assert;
import org.junit.Test;

public class BlackListAPIMapperTest{

    @Test
    public void fromDomain() {
        User blockingUser = new User();
        blockingUser.setId(1);
        blockingUser.setUsername("blockingUserTest");

        User blockedUser = new User();
        blockedUser.setId(2);
        blockedUser.setUsername("blockedUserTest");

        BlackList blackList = new BlackList();
        blackList.setBlockingUserId(blockingUser);
        blackList.setBlockedUserId(blockedUser);

        BlackListAPIResponseDTO blackListAPIResponseDTO = BlackListAPIMapper.fromDomain(blackList);

        Assert.assertEquals(blockingUser.getUsername(), blackListAPIResponseDTO.getBlockingUsername());
        Assert.assertEquals(blockedUser.getUsername(), blackListAPIResponseDTO.getBlockedUsername());

    }
}