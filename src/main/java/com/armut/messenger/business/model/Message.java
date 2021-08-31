package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = ProjectConstants.TableConstants.MESSAGE_TABLE_NAME)
public class Message extends AbstractBaseModel {

    @Column(name = ProjectConstants.TableConstants.Message.CONTENT, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = ProjectConstants.TableConstants.Message.FROM_USER_ID, nullable = false)
    private User fromUserId;

    @ManyToOne
    @JoinColumn(name = ProjectConstants.TableConstants.Message.TO_USER_ID, nullable = false)
    private User toUserId;


}
