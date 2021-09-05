package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.presentation.api.dto.message.MessageAPIResponseDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageAPIMapperTest {

    @Test
    public void fromDomain() {
        LocalDateTime localDateTime = LocalDateTime.now();

        User fromUser = new User();
        fromUser.setUsername("fromUserTest");

        User toUser = new User();
        toUser.setUsername("toUserTest");

        Message message = new Message();
        message.setFromUserId(fromUser);
        message.setToUserId(toUser);
        message.setContent("message");
        message.setCreationDate(localDateTime);

        MessageAPIResponseDTO messageAPIResponseDTO = MessageAPIMapper.fromDomain(message);

        Assert.assertEquals(message.getFromUserId().getUsername(), messageAPIResponseDTO.getFromUsername());
        Assert.assertEquals(message.getToUserId().getUsername(), messageAPIResponseDTO.getToUsername());
        Assert.assertEquals(message.getContent(), messageAPIResponseDTO.getYourMessage());
        Assert.assertEquals(message.getCreationDate(), messageAPIResponseDTO.getMessageCreatedTime());

    }

    @Test
    public void testFromDomain() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Message> messageList = new ArrayList<Message>();

        User fromUser = new User();
        fromUser.setUsername("fromUserTest");

        User toUser = new User();
        toUser.setUsername("toUserTest");

        Message message = new Message();
        message.setFromUserId(fromUser);
        message.setToUserId(toUser);
        message.setContent("message");
        message.setCreationDate(localDateTime);
        messageList.add(message);

        User fromUser2 = new User();
        fromUser.setUsername("fromUserTest2");

        User toUser2 = new User();
        toUser.setUsername("toUserTest2");

        Message message2 = new Message();
        message2.setFromUserId(fromUser2);
        message2.setToUserId(toUser2);
        message2.setContent("message2");
        message2.setCreationDate(localDateTime.plusDays(1));
        messageList.add(message2);

        List<MessageAPIResponseDTO> messageAPIResponseDTO = MessageAPIMapper.fromDomain(messageList);

        Assert.assertEquals(message.getFromUserId().getUsername(), messageAPIResponseDTO.get(0).getFromUsername());
        Assert.assertEquals(message.getToUserId().getUsername(), messageAPIResponseDTO.get(0).getToUsername());
        Assert.assertEquals(message.getContent(), messageAPIResponseDTO.get(0).getYourMessage());
        Assert.assertEquals(message.getCreationDate(), messageAPIResponseDTO.get(0).getMessageCreatedTime());

        Assert.assertEquals(message2.getFromUserId().getUsername(), messageAPIResponseDTO.get(1).getFromUsername());
        Assert.assertEquals(message2.getToUserId().getUsername(), messageAPIResponseDTO.get(1).getToUsername());
        Assert.assertEquals(message2.getContent(), messageAPIResponseDTO.get(1).getYourMessage());
        Assert.assertEquals(message2.getCreationDate(), messageAPIResponseDTO.get(1).getMessageCreatedTime());

    }
}