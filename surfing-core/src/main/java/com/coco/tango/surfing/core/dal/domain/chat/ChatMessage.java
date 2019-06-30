package com.coco.tango.surfing.core.dal.domain.chat;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天实体类
 *
 * @author ckli01
 * @date 2019-06-13
 */
@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1152865686051397835L;
    /**
     * 消息唯一Id
     * <p>
     * 群组Id + 发送人 +自增
     * [] + [] + []
     * 00 0000 0000
     * 10 0000 0000
     * 00 0000 0000
     */
    @Id
    private String id;

    /**
     * 消息发送状态
     * 记录 发送
     * 服务器   接收成功 / 接收失败
     * 客户端   已读 / 未读
     * 客户端   已接收 / 未接收
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
    private Object value;

    /**
     * 发送消息 用户编码
     * 10 0000 0000
     */
    private String sendUserCode;

    /**
     * 接受消息 用户编码
     * 10 0000 0000
     *
     * 第一次 建立连接 必须 传接收用户
     * 多人用户 则用 ； 隔开
     *
     */
    private String recvUserCode;

}

    
    
  