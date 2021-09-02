package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.SecurityConstants;
import com.armut.messenger.business.exception.APIException;
import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.blacklist.BlackListService;
import com.armut.messenger.business.service.user.UserService;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIResponseDTO;
import com.armut.messenger.presentation.api.dto.message.MessageAPIResponseDTO;
import com.armut.messenger.presentation.api.mapper.BlackListAPIMapper;
import com.armut.messenger.presentation.api.mapper.MessageAPIMapper;
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
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping(value = MappingConstants.BLACK_LIST_CONTROLLER_PATH)
public class BlackListAPIController {

    private final BlackListService blackListService;
    private final UserService userService;

    public BlackListAPIController(BlackListService blackListService, UserService userService) {
        this.blackListService = blackListService;
        this.userService = userService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<BlackListAPIResponseDTO>> blockUser(@RequestBody BlackListAPIRequestDTO blackListAPIRequestDTO,
                                                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

            final String authorization = request.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
            User blockingUser = userService.getUserByUsername(blackListAPIRequestDTO.getBlockingUsername());
            User blockedUser = userService.getUserByUsername(blackListAPIRequestDTO.getBlockedUsername());

            String[] arrOfStr = authorization.split(" ");
            String userToken = arrOfStr[1];
            if (userToken.equals(blockingUser.getToken())){
                Boolean existBlockingUsers = blackListService.existBlockingUserIdAndBlockedUserId(blockingUser,blockedUser);
                if (!existBlockingUsers){
                    BlackList blackList = new BlackList();
                    blackList.setBlockingUserId(blockingUser);
                    blackList.setBlockedUserId(blockedUser);

                    blackListService.save(blackList);

                    BlackListAPIResponseDTO blackListAPIResponseDTO = BlackListAPIMapper.fromDomain(blackList);

                    APIResponseDTO apiResponse = new APIResponseDTO<>(HttpStatus.OK,blackListAPIResponseDTO);
                    return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
                }
                else{
                    throw new APIException("You had blocked this user already.", HttpStatus.ACCEPTED);
                }
            }
            else{
                throw new APIException("Blocking Token not equal to authorization. You can't block user.", HttpStatus.UNAUTHORIZED);
            }

    }
}
