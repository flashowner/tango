package com.coco.tango.surfing.core.dal.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户信息
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Data
@TableName("t_user_wx")
public class WxUser extends BaseDomain implements Serializable {
    private static final long serialVersionUID = -2385868150369864836L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 微信应用唯一ID
     */
    private String appId;

    /**
     * 微信用户唯一ID
     */
    private String openId;

    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


}

    
    
  