package com.armut.messenger.business.repository;

import com.armut.messenger.business.model.Message;
import com.armut.messenger.business.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageJPARepository extends BaseJPARepository<Message,Long> {
    List<Message> findAllByFromUserId(User user);

    @Query(value = "SELECT u.username FROM user u, (SELECT from_user_id,to_user_id FROM message GROUP BY from_user_id,to_user_id " +
            "HAVING from_user_id=?1 " +
            "UNION " +
            "SELECT to_user_id AS from_user_id, from_user_id AS to_user_id FROM message GROUP BY from_user_id,to_user_id " +
            "HAVING to_user_id=?1) t WHERE u.record_id=t.to_user_id", nativeQuery = true)
    List<Object[]> getAllMessagingUserList(Long id);

    @Query(value = "SELECT * FROM ( " +
            "SELECT from_user_id, to_user_id, content, creation_date FROM message WHERE from_user_id=?1 AND to_user_id=?2 " +
            "UNION " +
            "SELECT from_user_id, to_user_id, content, creation_date FROM message WHERE from_user_id=?2 AND to_user_id=?1) t " +
            "order by t.creation_date", nativeQuery = true)
    List<Object[]> getAllMessagesBetweenTwoUser(Long fromUserId, Long toUserId);
}
