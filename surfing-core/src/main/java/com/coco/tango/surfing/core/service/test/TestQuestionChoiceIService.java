package com.coco.tango.surfing.core.service.test;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;

import java.util.Collection;
import java.util.List;

/**
 * 测试题 选项 服务入口
 *
 * @author ckli01
 * @date 2019-06-03
 */
public interface TestQuestionChoiceIService extends IService<TestQuestionChoice> {
    /**
     * 设置测试题目
     *
     * @param entityList
     */
    void saveTestQusChoice(Collection<TestQuestion> entityList);

    /**
     * 批量更新
     *
     * @param updateList
     */
    void updateBatch(List<TestQuestion> updateList);




}
