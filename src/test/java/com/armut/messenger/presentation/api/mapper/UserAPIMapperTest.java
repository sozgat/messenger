package com.armut.messenger.presentation.api.mapper;

import com.armut.messenger.business.model.User;
import com.armut.messenger.presentation.api.dto.user.UserAPIRequestDTO;
import com.armut.messenger.presentation.api.dto.user.UserAPIResponseDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAPIMapperTest {

    @Test
    public void fromDomain() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(30);
        User user = new User();
        user.setId(1);
        user.setVersion(0);
        user.setActive(true);
        user.setCreationDate(localDateTime);
        user.setUsername("Test");
        user.setToken("de21accc-8b09-48c8-8bc9-673438ce5b2f");
        user.setTokenExpiryDate(localDateTime);

        UserAPIResponseDTO userAPIResponseDTO = UserAPIMapper.fromDomain(user);

        Assert.assertEquals(user.getUsername(), userAPIResponseDTO.getUsername());
        Assert.assertEquals(user.getToken(), userAPIResponseDTO.getToken());
        Assert.assertEquals(user.getTokenExpiryDate(), userAPIResponseDTO.getTokenExpiryDate());
    }

    @Test
    public void testFromDomain() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(30);
        List<User> userList = new ArrayList<User>();

        User user1 = new User();
        user1.setUsername("Test1");
        user1.setToken("de21accc-8b09-48c8-8bc9-673438ce5b2f");
        user1.setTokenExpiryDate(localDateTime);
        userList.add(user1);

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setToken("a12794b0-753f-4e5f-b36c-69a08783df09");
        user2.setTokenExpiryDate(localDateTime);
        userList.add(user2);

        User user3 = new User();
        user3.setUsername("Test3");
        user3.setToken("510dd188-1872-45aa-bd10-07bb9551b67c");
        user3.setTokenExpiryDate(localDateTime);
        userList.add(user3);

        List<UserAPIResponseDTO> userAPIResponseDTO = UserAPIMapper.fromDomain(userList);

        Assert.assertEquals(user1.getUsername(), userAPIResponseDTO.get(0).getUsername());
        Assert.assertEquals(user1.getToken(), userAPIResponseDTO.get(0).getToken());
        Assert.assertEquals(user1.getTokenExpiryDate(), userAPIResponseDTO.get(0).getTokenExpiryDate());
        Assert.assertEquals(user2.getUsername(), userAPIResponseDTO.get(1).getUsername());
        Assert.assertEquals(user2.getToken(), userAPIResponseDTO.get(1).getToken());
        Assert.assertEquals(user2.getTokenExpiryDate(), userAPIResponseDTO.get(1).getTokenExpiryDate());
        Assert.assertEquals(user3.getUsername(), userAPIResponseDTO.get(2).getUsername());
        Assert.assertEquals(user3.getToken(), userAPIResponseDTO.get(2).getToken());
        Assert.assertEquals(user3.getTokenExpiryDate(), userAPIResponseDTO.get(2).getTokenExpiryDate());

    }

    @Test
    public void toDomain() {

        UserAPIRequestDTO userAPIRequestDTO = new UserAPIRequestDTO();
        userAPIRequestDTO.setUsername("test");
        userAPIRequestDTO.setPassword("123456");

        User user = UserAPIMapper.toDomain(userAPIRequestDTO);

        Assert.assertEquals(userAPIRequestDTO.getUsername(), user.getUsername());
        Assert.assertEquals(userAPIRequestDTO.getPassword(), user.getPassword());
    }
}