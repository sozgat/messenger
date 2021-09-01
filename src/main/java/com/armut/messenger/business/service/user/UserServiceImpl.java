package com.armut.messenger.business.service.user;

import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import com.armut.messenger.business.util.CryptUtil;
import org.springframework.stereotype.Service;

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
}
