package com.coco.tango.surfing.core.dal.domain.chat;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 聊天 群组
 *
 * @author ckli01
 * @date 2019-06-26
 */
@Data
public class ChatGroup implements Serializable {
    private static final long serialVersionUID = 5188944195086986307L;

    @Id
    private String id;

    /**
     * 群组Id
     */
    private String groupId;

    /**
     * 用户Id
     * // todo 修改数据结构
     */
    private String userCode;


    /**
     * 名称
     */
    private String name;


}

    
    
  