package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.exception.APIException;
import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.blacklist.BlackListService;
import com.armut.messenger.business.service.user.UserService;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.blacklist.BlackListAPIResponseDTO;
import com.armut.messenger.presentation.api.mapper.BlackListAPIMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

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
    public ResponseEntity<APIResponseDTO<BlackListAPIResponseDTO>> blockUser(@Valid @RequestBody BlackListAPIRequestDTO blackListAPIRequestDTO,
                                                                             HttpServletRequest request) throws Exception {
        User authBlockingUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER);
        User blockedUser = userService.getUserByUsername(blackListAPIRequestDTO.getBlockedUsername());

        log.info("Block Controller is calling - Auth UserID: " + authBlockingUser.getId());

        if (Objects.isNull(blockedUser)){
            throw new APIException("Blocked User is not found!", HttpStatus.ACCEPTED, blackListAPIRequestDTO);
        }
        if (authBlockingUser.equals(blockedUser)){
            throw new APIException("You can't block yourself!", HttpStatus.ACCEPTED, blackListAPIRequestDTO);
        }

        Boolean existBlockingUsers = blackListService.existBlockingUserIdAndBlockedUserId(authBlockingUser, blockedUser);
        if (!existBlockingUsers) {
            BlackList blackList = new BlackList();
            blackList.setBlockingUserId(authBlockingUser);
            blackList.setBlockedUserId(blockedUser);

            blackListService.save(blackList);

            log.info("Data is saved to BlackList succesfully. AuthUser ID:" + authBlockingUser.getId() +
                    " is blocked User ID:" + blockedUser.getId());

            BlackListAPIResponseDTO blackListAPIResponseDTO = BlackListAPIMapper.fromDomain(blackList);

            APIResponseDTO apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS,
                    HttpStatus.OK, blackListAPIResponseDTO);

            log.info("Block Controller is ending - Auth UserID: " + authBlockingUser.getId());
            return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
        } else {
            throw new APIException("You had blocked this user already.", HttpStatus.ACCEPTED, blackListAPIRequestDTO);
        }
    }
}
