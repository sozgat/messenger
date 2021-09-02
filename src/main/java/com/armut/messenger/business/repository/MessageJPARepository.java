package com.armut.messenger.business.repository;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;

import java.util.List;

public interface MessageJPARepository extends BaseJPARepository<Message,Long> {
    List<Message> findAllByFromUserId(User user);
}
