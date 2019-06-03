package com.coco.tango.surfing.core.dal.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


    private List<TestQuestionChioce> testQuestionChioces;


}

    
    

