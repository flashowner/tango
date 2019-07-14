package com.coco.tango.surfing.user.bean.vo;

import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import lombok.Data;

/**
 * 用户登录 返回
 *
 * @author ckli01
 * @date 2019-07-08
 */
@Data
public class TangoUserVO extends TangoUser {


    private static final long serialVersionUID = 2242600900367894959L;

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
     * 令牌
     */
    private String token;


    /**
     * 测试题完成 状态
     */
    private boolean testState;


}

    
    
  