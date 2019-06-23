package com.coco.tango.surfing.core.dal.domain.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试题选项
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Data
@TableName("t_test_question_chioce")
public class TestQuestionChioce extends BaseDomain implements Serializable {


    private static final long serialVersionUID = -5236114776895109424L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目主键
     */
    private Long questionId;

    /**
     * 内容
     */
    private String content;

    /**
     * 排序序号
     */
    private Integer orderNum;

    /**
     * 排序字符
     */
    private String orderChar;

    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;

}

    
    
  