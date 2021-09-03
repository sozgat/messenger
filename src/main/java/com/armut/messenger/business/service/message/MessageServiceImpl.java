package com.armut.messenger.business.service.message;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.MessageJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    private final MessageJPARepository messageJPARepository;

    public MessageServiceImpl(MessageJPARepository messageJPARepository) {
        super(messageJPARepository);
        this.messageJPARepository = messageJPARepository;
    }

    @Override
    public List<Message> getMessagesByFromUserId(User user) {
       return messageJPARepository.findAllByFromUserId(user);
    }

    @Override
    public List<String> getAllMessagingUserList(Long id) {
        List<Object[]>  allMessagingUserList = messageJPARepository.getAllMessagingUserList(id);
        List<String> returnList = new ArrayList<>();
        for (Object[] allMessagingUser : allMessagingUserList) {
            returnList.add( (String) allMessagingUser[0]);
        }
        return returnList;
    }
}
