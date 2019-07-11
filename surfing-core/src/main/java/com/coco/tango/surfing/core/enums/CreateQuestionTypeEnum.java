package com.coco.tango.surfing.core.enums;

import lombok.Getter;

/**
 * 测试题目 类型 枚举
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Getter
public enum CreateQuestionTypeEnum {


    SYSTEM(0, "系统题目"),

    USER_DEFINED(1, "用户自定义题目"),


    ;


    private Integer type;

    private String desc;

    CreateQuestionTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


}
