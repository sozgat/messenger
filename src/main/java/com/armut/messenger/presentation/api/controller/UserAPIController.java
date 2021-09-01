package com.armut.messenger.presentation.api.controller;

import com.armut.messenger.business.constant.MappingConstants;
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
    public ResponseEntity<APIResponseDTO<List<UserAPIResponseDTO>>> getUsers(){

        List<UserAPIResponseDTO> userAPIResponseDTO = UserAPIMapper.fromDomain(userService.findAll());

        APIResponseDTO<List<UserAPIResponseDTO>> apiResponse = new APIResponseDTO<>(HttpStatus.OK,userAPIResponseDTO);

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<UserAPIResponseDTO>> createUser(@RequestBody UserAPIRequestDTO userAPIRequestDTO){
        try{
            User user = UserAPIMapper.toDomain(userAPIRequestDTO);
            userService.save(user);

            UserAPIResponseDTO userAPIResponseDTO = UserAPIMapper.fromDomain(user);

            APIResponseDTO apiResponse = new APIResponseDTO<>(HttpStatus.OK,userAPIResponseDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }
        catch (Exception e){
            throw new RuntimeException("Error error!");
        }
    }

    @RequestMapping(value = "/login")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<APIResponseDTO<UserAPIResponseDTO>> loginUser(@RequestBody UserAPIRequestDTO userAPIRequestDTO){
        try{
            User user = UserAPIMapper.toDomain(userAPIRequestDTO);
            User existUser = userService.existUser(user);
            userService.setToken(existUser);
            UserAPIResponseDTO userAPIResponseDTO = UserAPIMapper.fromDomain(existUser);

            APIResponseDTO apiResponse = new APIResponseDTO<>(HttpStatus.OK,userAPIResponseDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }
        catch (Exception e){
            throw new RuntimeException("Error error!");
        }
    }
}
