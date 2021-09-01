package com.armut.messenger.business.service.message;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.repository.MessageJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    private final MessageJPARepository messageJPARepository;

    public MessageServiceImpl(MessageJPARepository messageJPARepository) {
        super(messageJPARepository);
        this.messageJPARepository = messageJPARepository;
    }
}
