package com.armut.messenger.business.service.blacklist;

import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.BaseService;

public interface BlackListService extends BaseService<BlackList> {
    Boolean existBlockingUserIdAndBlockedUserId(User blockingUser, User blockedUser);
}
