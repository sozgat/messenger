package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.user.UserService;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import com.armut.messenger.presentation.api.dto.user.UserAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.user.UserAPIResponseDTO;
import com.armut.messenger.presentation.api.mapper.UserAPIMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = MappingConstants.USER_CONTROLLER_PATH)
public class UserAPIController {

    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO<List<UserAPIResponseDTO>>> getUsers() throws Exception {

        log.info("getUsers Controller is calling.");

        List<UserAPIResponseDTO> userAPIResponseDTO = UserAPIMapper.fromDomain(userService.findAll());

        APIResponseDTO<List<UserAPIResponseDTO>> apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, userAPIResponseDTO);

        log.info("getUsers Controller is ending.");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<UserAPIResponseDTO>> signupUser(@Valid @RequestBody UserAPIRequestDTO userAPIRequestDTO) throws Exception {

        log.info("signupUser Controller is calling.");

        User user = UserAPIMapper.toDomain(userAPIRequestDTO);
        userService.save(user);

        log.info("signupUser: Data is saved to User succesfully. UserID: " + user.getId());

        UserAPIResponseDTO userAPIResponseDTO = UserAPIMapper.fromDomain(user);

        APIResponseDTO apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, userAPIResponseDTO);

        log.info("signupUser Controller is ending.");
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @RequestMapping(value = "/login")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<UserAPIResponseDTO>> loginUser(@Valid @RequestBody UserAPIRequestDTO userAPIRequestDTO) throws Exception {

        log.info("loginUser Controller is calling.");

        User user = UserAPIMapper.toDomain(userAPIRequestDTO);
        User existUser = userService.existUser(user);
        userService.setToken(existUser);

        log.info("loginUser: User is logging succesfully. UserID: " + existUser.getId());

        UserAPIResponseDTO userAPIResponseDTO = UserAPIMapper.fromDomain(existUser);

        APIResponseDTO apiResponse = new APIResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_SUCCESS, HttpStatus.OK, userAPIResponseDTO);

        log.info("loginUser Controller is ending.");
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
