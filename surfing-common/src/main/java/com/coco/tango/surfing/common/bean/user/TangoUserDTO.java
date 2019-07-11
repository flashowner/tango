package com.coco.tango.surfing.common.bean.user;

import lombok.Data;

import java.util.Date;

/**
 * Tango用户信息
 *
 * @author ckli01
 * @date 2019-06-06
 */
@Data
public class TangoUserDTO {


    /**
     * 主键
     */
    private Long id;


    /**
     * 编码
     */
    private String code;


    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthDay;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String professional;

    /**
     * 头像地址
     */
    private String avatarUrl;



}

    
    
  