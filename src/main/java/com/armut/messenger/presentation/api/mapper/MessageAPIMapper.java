package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.presentation.api.dto.message.MessageAPIResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

// @Component
public class MessageAPIMapper {

    public static MessageAPIResponseDTO fromDomain(Message message){
        MessageAPIResponseDTO messageAPIResponseDTO = new MessageAPIResponseDTO();

        messageAPIResponseDTO.setFromUsername(message.getFromUserId().getUsername());
        messageAPIResponseDTO.setToUsername(message.getToUserId().getUsername());
        messageAPIResponseDTO.setYourMessage(message.getContent());
        messageAPIResponseDTO.setMessageCreatedTime(message.getCreationDate());

        return messageAPIResponseDTO;
    }

    public static List<MessageAPIResponseDTO> fromDomain(List<Message> messages){
        return messages.stream().map(m -> MessageAPIMapper.fromDomain(m)).collect(Collectors.toList());
    }

   /* public static Message toDomain(MessageAPIRequestDTO messageAPIRequestDTO){
        Message message = new Message();


        message.setFromUserId(messageAPIRequestDTO.getFromUsername());
        message.setToUserId(messageAPIRequestDTO.getToUsername());
        message.setContent(messageAPIRequestDTO.getYourMessage());

        return message;
    } */

}
