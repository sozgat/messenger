package com.armut.messenger.business.service.user;

import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserJPARepository userJPARepository;

    public UserServiceImpl(UserJPARepository userJPARepository) {
        super(userJPARepository);
        this.userJPARepository = userJPARepository;
    }
}
