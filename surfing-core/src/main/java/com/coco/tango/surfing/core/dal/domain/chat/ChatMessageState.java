package com.coco.tango.surfing.core.dal.domain.chat;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户消息 接收状态
 *
 * @author ckli01
 * @date 2019-06-27
 */
@Data
public class ChatMessageState implements Serializable {
    private static final long serialVersionUID = -5084678452518449033L;


    @Id
    private String id;

    /**
     * 接收用户Code
     */
    private String userCode;

    /**
     * 消息Id
     */
    private String chatMessageId;

    /**
     * 状态码
     */
    private String code;

}

    
    
  