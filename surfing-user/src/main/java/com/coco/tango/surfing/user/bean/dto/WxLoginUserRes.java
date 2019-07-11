package com.coco.tango.surfing.user.bean.dto;

import lombok.Data;

/**
 * 微信登录 用户信息 解密
 *
 * @author ckli01
 * @date 2019-07-08
 */
@Data
public class WxLoginUserRes {


    /**
     * 微信 该应用 唯一 APPID
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户 唯一Id
     */
    private String unionId;

    /**
     * 水印
     */
    private WxLoginUserWatermark watermark;


}

    
    
  