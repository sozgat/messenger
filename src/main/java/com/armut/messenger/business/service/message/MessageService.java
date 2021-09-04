package com.armut.messenger.business.service.message;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.BaseService;

import java.util.List;

public interface MessageService extends BaseService<Message> {
    List<Message> getMessagesByFromUserId(User user);
    List<String> getAllMessagingUserList(Long id);
    List<Message> getAllMessagingBetweenTwoUser(Long fromUserId, Long toUserId);
}
