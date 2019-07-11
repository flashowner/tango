package com.coco.tango.surfing.core.enums;

import lombok.Getter;

/**
 * 题目类型
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Getter
public enum TestQuestionEnum {


    CHOICE(0, "选择题"),

    FILL_BLANK(1, "填空题"),


    ;


    private Integer type;

    private String desc;

    TestQuestionEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


}
