package com.coco.tango.surfing.user.bean.dto;

import lombok.Data;

/**
 * 微信 登录 返回信息
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Data
public class WxLoginRes {


    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    private String unionid;

    /**
     * 错误码:
     * -1	系统繁忙，此时请开发者稍候再试
     * 0	请求成功
     * 40029	code 无效
     * 45011	频率限制，每个用户每分钟100次
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;


}

    
    
  