package com.armut.messenger.business.service.message;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.MessageJPARepository;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    private final MessageJPARepository messageJPARepository;
    private final UserJPARepository userJPARepository;

    public MessageServiceImpl(MessageJPARepository messageJPARepository, UserJPARepository userJPARepository) {
        super(messageJPARepository);
        this.messageJPARepository = messageJPARepository;
        this.userJPARepository = userJPARepository;
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

    @Override
    public List<Message> getAllMessagingBetweenTwoUser(Long fromUserId, Long toUserId) {
        List<Object[]>  allMessages = messageJPARepository.getAllMessagesBetweenTwoUser(fromUserId,toUserId);
        List<Message> returnList = new ArrayList<>();
        for (Object[] message : allMessages){
            Message message1 = new Message();
            message1.setFromUserId(userJPARepository.getById(((BigInteger) message[0]).longValue()));
            message1.setToUserId(userJPARepository.getById(((BigInteger) message[1]).longValue()));
            message1.setContent((String) message[2]);
            message1.setCreationDate(Timestamp.valueOf(message[3].toString()).toLocalDateTime());
            returnList.add(message1);
        }
        return returnList;
    }
}
