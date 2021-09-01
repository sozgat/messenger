package com.armut.messenger.business.repository;

import com.armut.messenger.business.model.User;

public interface UserJPARepository extends BaseJPARepository<User,Long> {
    User findByUsername(String username);
}
