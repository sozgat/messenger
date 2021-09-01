package com.armut.messenger.business.service.user;

import com.armut.messenger.business.model.User;
import com.armut.messenger.business.service.BaseService;

public interface UserService extends BaseService<User> {
    User existUser(User user);
    User getUserByUsername(String username);
    void setToken(User user);

}
