package com.coco.tango.surfing.match.bean.vo;

import lombok.Data;

/**
 * 匹配人
 *
 * @author ckli01
 * @date 2019-07-11
 */
@Data
public class MatchPeopleVO {


    /**
     * 用户code
     */
    private String code;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 匹配度
     */
    private Double match;

    /**
     * 描述
     */
    private String description;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer sex;


}

    
    
  