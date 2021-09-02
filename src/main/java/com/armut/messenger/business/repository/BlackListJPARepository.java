package com.armut.messenger.business.repository;

import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;

public interface BlackListJPARepository extends BaseJPARepository<BlackList,Long> {
    Boolean existsByBlockingUserIdAndBlockedUserId(User blockingUser, User blockedUser);
}
