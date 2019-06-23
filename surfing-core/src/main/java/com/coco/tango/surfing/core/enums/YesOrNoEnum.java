package com.coco.tango.surfing.core.enums;

import lombok.Getter;

/**
 * 数据状态
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Getter
public enum YesOrNoEnum {
    YES(1, "是"),
    NO(0, "否");

    private Integer value;
    private String desc;

    YesOrNoEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
