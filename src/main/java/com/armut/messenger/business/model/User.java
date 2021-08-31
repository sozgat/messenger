package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = ProjectConstants.TableConstants.USER_TABLE_NAME)
public class User extends AbstractBaseModel {

    @Column(name = ProjectConstants.TableConstants.User.USERNAME, length = 20, unique = true, nullable = false)
    private String username;

    @Column(name = ProjectConstants.TableConstants.User.PASSWORD, nullable = false)
    private String password;

}
