package com.coco.tango.surfing.core.dal.domain;

import lombok.Data;

import java.util.Date;

/**
 * domain 公用
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Data
public class BaseDomain {


    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 更新时间
     */
    private Date updateDatetime;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;

}

    
    
  