package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.constant.SecurityConstants;
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
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<APIResponseDTO<MessageAPIResponseDTO>> sendMessage(@RequestBody MessageAPIRequestDTO messageAPIRequestDTO,
                                                                             HttpServletRequest request, HttpServletResponse response){
            //TODO: Kullanıcı kendine mesaj gönderebilir mi? Kendini bloklayabilir mi? (Extreme case)
            final String authorization = request.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
            User fromUser = userService.getUserByUsername(messageAPIRequestDTO.getFromUsername());
            User toUser = userService.getUserByUsername(messageAPIRequestDTO.getToUsername());

            String[] arrOfStr = authorization.split(" ");
            String userToken = arrOfStr[1];
            if (userToken.equals(fromUser.getToken())){
                log.info("User " + fromUser.getUsername() + " - ID:" + fromUser.getId() + "is auth. Function: sendMessage");
                Boolean existBlockingUsers = blackListService.existBlockingUserIdAndBlockedUserId(toUser,fromUser);
                    if (!existBlockingUsers){
                        Message message = new Message();
                        message.setContent(messageAPIRequestDTO.getYourMessage());
                        message.setFromUserId(fromUser);
                        message.setToUserId(toUser);
                        messageService.save(message);

                        MessageAPIResponseDTO messageAPIResponseDTO = MessageAPIMapper.fromDomain(message);

                        log.info("User " + fromUser.getUsername() + " - ID:" + fromUser.getId() + "sent message. Function: sendMessage: " +
                                messageAPIResponseDTO);


                        APIResponseDTO apiResponse = new APIResponseDTO<>(HttpStatus.OK,messageAPIResponseDTO);

                        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
                    }
                else{
                    throw new APIException("You blocked from "+ toUser.getUsername() +". You can't send message.",HttpStatus.FORBIDDEN);
                }
            }
            else{
                throw new APIException("FromUser Token not equal to authorization. You can't send message.", HttpStatus.UNAUTHORIZED );
            }
    }

    @GetMapping(value = "/getMessagingList")
    public ResponseEntity<APIResponseDTO<List<String>>> MessagingList(HttpServletRequest request){

        try {
            final String authorization = request.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
            String[] arrOfStr = authorization.split(" ");
            String userToken = arrOfStr[1];

            User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);

            List<String> allMessagingUserList = messageService.getAllMessagingUserList(authUser.getId());

            APIResponseDTO<List<String>> apiResponse = new APIResponseDTO<>(HttpStatus.OK,allMessagingUserList);

            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

        }
        catch (Exception e){
            throw new RuntimeException("Error error!");
        }
    }


    @GetMapping
    public ResponseEntity<APIResponseDTO<List<MessageAPIResponseDTO>>> getMessagesByFromUser(@RequestBody MessageAPIGetMessagesRequestDTO messageAPIGetMessagesRequestDTO,
                                                                                HttpServletRequest request, HttpServletResponse response){

        try {
            final String authorization = request.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
            User toUser = userService.getUserByUsername(messageAPIGetMessagesRequestDTO.getUsername());
            User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);


            List<MessageAPIResponseDTO> messageAPIResponseDTO = MessageAPIMapper.fromDomain(messageService.getMessagesByFromUserId(toUser));

                APIResponseDTO<List<MessageAPIResponseDTO>> apiResponse = new APIResponseDTO<>(HttpStatus.OK,messageAPIResponseDTO);

                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

        }
        catch (Exception e){
            throw new RuntimeException("Error error!");
        }
    }

}
