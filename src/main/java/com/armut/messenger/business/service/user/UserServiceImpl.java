package com.armut.messenger.business.service.user;

import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import com.armut.messenger.business.util.CryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserJPARepository userJPARepository;

    public UserServiceImpl(UserJPARepository userJPARepository) {
        super(userJPARepository);
        this.userJPARepository = userJPARepository;
    }

    @Override
    public synchronized User save(User user) {
        // Set password if new customer.
        if (user.getId() == ProjectConstants.ID_UNSAVED_VALUE) {
            user.setPassword(CryptUtil.encrypt(user.getPassword()));
        }
        // Save customer.
        user = super.save(user);
        return user;
    }

    @Override
    public User existUser(User user) {

        User existUser = userJPARepository.findByUsername(user.getUsername());
        if (!Objects.isNull(existUser)) {
            if (existUser.getPassword().equals(CryptUtil.encrypt(user.getPassword()))) {
                return existUser;
            } else {
                throw new RuntimeException("Invalid Login: Password is incorrect! \n Username: " + user.getUsername());
            }
        } else {
            throw new RuntimeException("Invalid Login: Username Not Found! \n Username: " + user.getUsername());
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userJPARepository.findByUsername(username);
    }

    @Override
    public User getUserByToken(User user) {
        return userJPARepository.findByToken(user.getToken());
    }

    @Override
    public void setToken(User user) {


        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiryDate(LocalDateTime.now().plusDays(30));

        super.save(user);
    }

}
