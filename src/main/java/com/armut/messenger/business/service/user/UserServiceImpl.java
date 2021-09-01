package com.armut.messenger.business.service.user;

import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.business.service.BaseServiceImpl;
import com.armut.messenger.business.util.CryptUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

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
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            throw new RuntimeException("Username or Password False!");
        }
        else{
                User existUser = userJPARepository.findByUsername(user.getUsername());
            if (!existUser.getUsername().isEmpty()) {
                if (existUser.getPassword().equals(CryptUtil.encrypt(user.getPassword()))){
                    return existUser;
                }
                else{
                    throw new RuntimeException("Password is incorrect!");
                }
            }
            else{
                throw new RuntimeException("Username Not Found!");
            }
        }
    }

    @Override
    public void setToken(User user) {
        // Creating the LocalDatetime object
        LocalDate currentLocalDate = LocalDate.now().plusDays(30);

        // Getting system timezone
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // converting LocalDateTime to ZonedDateTime with the system timezone
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);

        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
        Date utilDate = Date.from(zonedDateTime.toInstant());

        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiryDate(utilDate);

        super.save(user);
    }

}
