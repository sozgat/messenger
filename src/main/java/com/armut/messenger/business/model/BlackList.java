package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = ProjectConstants.TableConstants.BLACK_LIST_TABLE_NAME)
public class BlackList extends AbstractBaseModel {

    @OneToOne
    @JoinColumn(name = ProjectConstants.TableConstants.BlackList.BLOCKING_USER_ID, nullable = false)
    private User blockingUserId;

    @OneToOne
    @JoinColumn(name = ProjectConstants.TableConstants.BlackList.BLOCKED_USER_ID, nullable = false)
    private User blockedUserId;
}
