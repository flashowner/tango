package com.coco.tango.surfing.core.dal.repository.chat;

import com.coco.tango.surfing.core.dal.domain.chat.ChatGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * 聊天群组
 *
 * @author ckli01
 * @date 2019-06-26
 */
public interface ChatGroupRepository extends MongoRepository<ChatGroup, String> {


    /**
     * 根据 群组 Id 查询
     * @param groupId
     * @return
     */
    @Query(value = "{\"groupId\":{\"$eq\":?0}}")
    List<ChatGroup> searchByGroupId(String groupId);

    /**
     * 根据 用户 code 查询
     * @param sendUserCode
     * @return
     */
    @Query(value = "{\"userCode\":{\"$eq\":?0}}")
    List<ChatGroup> searchByUserCode(String sendUserCode);
}
