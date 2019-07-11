package com.coco.tango.surfing.core.dal.domain.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 答题 记录
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Data
public class UserQuestionAnswer extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 2189695859608659719L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 题目Id
     */
    private Long questionId;


    /**
     * 用户作答
     */
    private String answer;


    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


}

    
    
  