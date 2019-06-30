package com.coco.tango.surfing.chat.enums;

import lombok.Getter;

/**
 * 聊天消息 code 枚举
 *
 * @author ckli01
 * @date 2019-06-26
 */
@Getter
public enum ChatMessageCodeEnum {

    CODE_SERVER_SUCCESS("10000", "服务器 发送给 客户端 服务器接收成功"),
    CODE_SERVER_FAILED("10100", "服务器 发送给 客户端 服务器接收失败"),


    CODE_CLIENT_UNKNOW("10200", "客户端 发送给 服务器 消息接收状态未知"),

    CODE_CLIENT_SUCCESS("10300", "客户端 发送给 服务器 客户端接收成功"),

    CODE_READ("10400", "消息已读"),
    CODE_UNREAD("10500", "消息未读"),


    ;
    private String code;


    private String desc;


    ChatMessageCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
