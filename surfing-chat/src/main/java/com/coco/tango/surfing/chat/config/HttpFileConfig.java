package com.coco.tango.surfing.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Http 相关文件配置
 *
 * @author ckli01
 * @date 2019-06-25
 */
@Component
@ConfigurationProperties(prefix = "tango.http.upload")
@Data
public class HttpFileConfig {


    /**
     * 图片上传保存地址
     */
    private String imgPath;

    /**
     * 语音上传保存地址
     */
    private String yyPath;

    /**
     * 图片请求前缀
     */
    private String imgRequestPath;

    /**
     * 语音请求前缀
     */
    private String yyRequestPath;


}

    
    
  