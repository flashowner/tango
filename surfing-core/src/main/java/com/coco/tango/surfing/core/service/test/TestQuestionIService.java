package com.coco.tango.surfing.core.service.test;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;

import java.util.List;

/**
 * 测试题 服务入口
 *
 * @author ckli01
 * @date 2019-06-03
 */
public interface TestQuestionIService extends IService<TestQuestion> {


    /**
     * 获取 所有 题目
     * @return
     */
    List<TestQuestion> selectAll(TestQuestion testQuestion);


    /**
     * 获取 系统题目
     * @return
     */
    List<TestQuestion> systemQuestion();
}
