package com.coco.tango.surfing.chat.bean;

import lombok.Data;

import java.util.Date;

/**
 * 聊天实体类
 *
 * @author ckli01
 * @date 2019-06-13
 */
@Data
public class ChatMessage {

    /**
     * 消息唯一Id
     */
    private String id;

    /**
     * 消息发送状态
     * 记录 发送
     * 成功 / 失败
     * 已读 / 未读
     */
    private String code;

    /**
     * 聊天群组Id
     */
    private String groupId;

    /**
     * 发送消息时间
     */
    private Date time;

    /**
     * 消息类型
     * 区分 文字 图片 表情 语音等
     */
    private String type;

    /**
     * 消息值
     */
    private String value;

    /**
     * 发送消息 用户编码
     */
    private String sendUserCode;

    /**
     * 接受消息 用户编码
     */
    private String recvUserCode;

}

    
    
  