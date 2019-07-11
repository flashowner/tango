package com.coco.tango.surfing.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置
 *
 * @author ckli01
 * @date 2019-07-08
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxConfig {


    /**
     * 小程序 APPID
     */
    private String appId;

    /**
     * 小程序 加密 SECRET
     */
    private String secret;


}

    
    
  