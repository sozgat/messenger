package com.armut.messenger.business.service.blacklist;

import com.armut.messenger.business.model.BlackList;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.BlackListJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlackListServiceImpl extends BaseServiceImpl<BlackList> implements BlackListService {

    private final BlackListJPARepository blackListJPARepository;

    public BlackListServiceImpl(BlackListJPARepository blackListJPARepository){
        super(blackListJPARepository);
        this.blackListJPARepository = blackListJPARepository;
    }

    @Override
    public Boolean existBlockingUserIdAndBlockedUserId(User blockingUser, User blockedUser) {
        return blackListJPARepository.existsByBlockingUserIdAndBlockedUserId(blockingUser,blockedUser);
    }
}
