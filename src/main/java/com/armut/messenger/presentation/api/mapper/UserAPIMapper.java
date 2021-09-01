package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.User;
import com.armut.messenger.presentation.api.dto.user.UserAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.user.UserAPIResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserAPIMapper {

    public static UserAPIResponseDTO fromDomain(User user){
        UserAPIResponseDTO userAPIResponseDTO = new UserAPIResponseDTO();

        userAPIResponseDTO.setUsername(user.getUsername());
        return userAPIResponseDTO;
    }

    public static List<UserAPIResponseDTO> fromDomain(List<User> users){
        return users.stream().map(u -> UserAPIMapper.fromDomain(u)).collect(Collectors.toList());
    }

    public static User toDomain(UserAPIRequestDTO userAPIRequestDTO){
        User user = new User();

        user.setUsername(userAPIRequestDTO.getUsername());
        user.setPassword(userAPIRequestDTO.getPassword());

        return user;
    }

}
