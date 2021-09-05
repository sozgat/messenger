package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.exception.APIException;
import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.blacklist.BlackListService;
import com.armut.messenger.business.service.message.MessageService;
import com.armut.messenger.business.service.user.UserService;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIGetMessagesRequestDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIResponseDTO;
import com.armut.messenger.presentation.api.mapper.MessageAPIMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = MappingConstants.MESSAGE_CONTROLLER_PATH)
public class MessageAPIController {

    private final MessageService messageService;
    private final UserService userService;
    private final BlackListService blackListService;

    public MessageAPIController(MessageService messageService, UserService userService, BlackListService blackListService) {
        this.messageService = messageService;
        this.userService = userService;
        this.blackListService = blackListService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<MessageAPIResponseDTO>> sendMessage(@Valid @RequestBody MessageAPIRequestDTO messageAPIRequestDTO,
                                                                             HttpServletRequest request) throws Exception {
        //TODO: Kullanıcı kendine mesaj gönderebilir mi? Kendini bloklayabilir mi? (Extreme case)
        User authFromUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);
        User toUser = userService.getUserByUsername(messageAPIRequestDTO.getToUsername());

        log.info("sendMessage Controller is calling - Auth UserID: " + authFromUser.getId());

        Boolean existBlockingUsers = blackListService.existBlockingUserIdAndBlockedUserId(toUser, authFromUser);
        if (!existBlockingUsers) {
            Message message = new Message();
            message.setContent(messageAPIRequestDTO.getYourMessage());
            message.setFromUserId(authFromUser);
            message.setToUserId(toUser);
            messageService.save(message);

            MessageAPIResponseDTO messageAPIResponseDTO = MessageAPIMapper.fromDomain(message);

            log.info("sendMessage: User " + authFromUser.getUsername() + " - ID: " + authFromUser.getId() + " sent message. Function: sendMessage: " +
                    messageAPIResponseDTO);

            APIResponseDTO apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, messageAPIResponseDTO);

            log.info("sendMessage Controller is ending - Auth UserID: " + authFromUser.getId());
            return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
        } else {
            throw new APIException("You blocked from "+ toUser.getUsername() +". You can't send message.", HttpStatus.FORBIDDEN, messageAPIRequestDTO);
        }
    }

    @GetMapping(value = "/userlist")
    public ResponseEntity<APIResponseDTO<List<String>>> messagingUserList(HttpServletRequest request) throws Exception {

        User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);
        log.info("messagingUserList Controller is calling - Auth UserID: " + authUser.getId());

        List<String> allMessagingUserList = messageService.getAllMessagingUserList(authUser.getId());

        log.info("messagingUserList: Auth UserID: " + authUser.getId() + " - Response Data: " + allMessagingUserList);

        APIResponseDTO<List<String>> apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, allMessagingUserList);

        log.info("messagingUserList Controller is ending - Auth UserID: " + authUser.getId());
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }


    @PostMapping(value = "/mymessages")
    public ResponseEntity<APIResponseDTO<List<MessageAPIResponseDTO>>> getMessagesByFromUser(@Valid @RequestBody MessageAPIGetMessagesRequestDTO messageAPIGetMessagesRequestDTO,
                                                                                             HttpServletRequest request) throws Exception {

        User toUser = userService.getUserByUsername(messageAPIGetMessagesRequestDTO.getUsername());
        User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);
        log.info("getMessagesByFromUser Controller is calling - Auth UserID: " + authUser.getId());

        List<Message> allMessages = messageService.getAllMessagingBetweenTwoUser(authUser.getId(), toUser.getId());

        List<MessageAPIResponseDTO> messageAPIResponseDTO = MessageAPIMapper.fromDomain(allMessages);

        log.info("getMessagesByFromUser: Auth UserID: " + authUser.getId() + " - Response Data: " + messageAPIResponseDTO);

        APIResponseDTO<List<MessageAPIResponseDTO>> apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, messageAPIResponseDTO);

        log.info("getMessagesByFromUser Controller is ending - Auth UserID: " + authUser.getId());
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);

    }

}
