package com.coco.tango.surfing.match.bean.vo;

import lombok.Data;

/**
 * 用户 作答传输类
 *
 * @author ckli01
 * @date 2019-07-09
 */

@Data
public class    UserQuestionAnswerVO {

    /**
     * 题目Id
     */
    private Long questionId;


    /**
     * 用户作答
     */
    private String answer;


    /**
     * 分数
     */
    private Double score;


}

    
    
  