package com.coco.tango.surfing.user.bean.dto;

import lombok.Data;

/**
 * 微信 登录 用户 信息 解密 水印
 *
 * @author ckli01
 * @date 2019-07-08
 */
@Data
public class WxLoginUserWatermark {


    /**
     * 小程序 唯一Id
     */
    private String appId;

    /**
     * 时间戳
     */
    private String timestamp;



}

    
    
  