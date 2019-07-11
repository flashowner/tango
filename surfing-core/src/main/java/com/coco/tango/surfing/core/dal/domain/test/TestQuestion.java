package com.coco.tango.surfing.core.dal.domain.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 测试题
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Data
@TableName("t_test_question")
public class TestQuestion extends BaseDomain implements Serializable {


    private static final long serialVersionUID = -1422409796936206542L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 排序
     */
    private Integer orderNum;


    /**
     * 创建类型 0-系统题目 1-用户自定义题目
     */
    private Integer createType;


    /**
     * 题目类型 0-选择题 1-填空题
     */
    private Integer questionType;

    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


    /**
     * 题目选项信息
     */
    private List<TestQuestionChoice> testQuestionChoices;


}

    
    

