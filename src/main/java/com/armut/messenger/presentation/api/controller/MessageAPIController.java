package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.SecurityConstants;
import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.message.MessageService;
import com.armut.messenger.business.service.user.UserService;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIResponseDTO;
import com.armut.messenger.presentation.api.dto.user.UserAPIResponseDTO;
import com.armut.messenger.presentation.api.mapper.MessageAPIMapper;
import com.armut.messenger.presentation.api.mapper.UserAPIMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = MappingConstants.MESSAGE_CONTROLLER_PATH)
public class MessageAPIController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageAPIController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<MessageAPIResponseDTO>> sendMessage(@RequestBody MessageAPIRequestDTO messageAPIRequestDTO,
                                                                             HttpServletRequest request, HttpServletResponse response){
        try{
            final String authorization = request.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
            User fromUser = userService.getUserByUsername(messageAPIRequestDTO.getFromUsername());
            User toUser = userService.getUserByUsername(messageAPIRequestDTO.getToUsername());

            String[] arrOfStr = authorization.split(" ");
            String userToken = arrOfStr[1];
            if (userToken.equals(fromUser.getToken())){
                Message message = new Message();
                message.setContent(messageAPIRequestDTO.getYourMessage());
                message.setFromUserId(fromUser);
                message.setToUserId(toUser);
                messageService.save(message);

                MessageAPIResponseDTO messageAPIResponseDTO = MessageAPIMapper.fromDomain(message);

                APIResponseDTO apiResponse = new APIResponseDTO<>(HttpStatus.OK,messageAPIResponseDTO);
                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
            }
            throw new RuntimeException("FromUser Token not equal to authorization. You couldn't send message.");
        }
        catch (Exception e){
            throw new RuntimeException("Error error!");
        }
    }

}
