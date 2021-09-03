package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = ProjectConstants.TableConstants.USER_TABLE_NAME)
public class User extends AbstractBaseModel {

    @Column(name = ProjectConstants.TableConstants.User.USERNAME, length = 20, unique = true, nullable = false)
    private String username;

    @Column(name = ProjectConstants.TableConstants.User.PASSWORD, nullable = false)
    private String password;

    @Column(name = ProjectConstants.TableConstants.User.TOKEN)
    private String token;

    @Column(name = ProjectConstants.TableConstants.User.TOKEN_EXPIRY_DATE)
    private LocalDateTime tokenExpiryDate;
}
