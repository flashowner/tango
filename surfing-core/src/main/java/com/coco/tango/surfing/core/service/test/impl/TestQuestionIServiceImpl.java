package com.coco.tango.surfing.core.service.test.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.mapper.test.TestQuestionMapper;
import com.coco.tango.surfing.core.enums.CreateQuestionTypeEnum;
import com.coco.tango.surfing.core.service.test.TestQuestionChoiceIService;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 测试题 实现类
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Service
@Slf4j
public class TestQuestionIServiceImpl extends ServiceImpl<TestQuestionMapper, TestQuestion>
        implements TestQuestionIService {


    @Autowired
    private TestQuestionChoiceIService testQuestionChoiceIService;


    @Override
    public List<TestQuestion> selectAll(TestQuestion testQuestion) {
        return super.getBaseMapper().selectAll(testQuestion);
    }


    /**
     * 获取 系统 题目
     * <p>
     * todo 缓存 管理
     *
     * @return
     */
    @Override
    public List<TestQuestion> systemQuestion() {
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setCreateType(CreateQuestionTypeEnum.SYSTEM.getType());
        return selectAll(testQuestion);
    }

    @Override
    public List<Long> systemQuestionIds() {
        return super.baseMapper.systemQuestionIds(CreateQuestionTypeEnum.SYSTEM.getType(), null);
    }


    @Override
    public List<TestQuestion> userQus(String userCode) {
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setCreateType(CreateQuestionTypeEnum.USER_DEFINED.getType());
        testQuestion.setCreateUser(userCode);
        return this.selectAll(testQuestion);
    }


    @Override
    @Transactional
    public boolean saveBatch(Collection<TestQuestion> entityList) {

        if (!CollectionUtils.isEmpty(entityList)) {
            super.saveBatch(entityList);
            testQuestionChoiceIService.saveTestQusChoice(entityList);
        } else {
            log.info(" user save qus for entity is empty");
        }


        return true;
    }


    @Override
    @Transactional
    public void updateBatch(List<TestQuestion> updateList) {
        if (!CollectionUtils.isEmpty(updateList)) {
            super.updateBatchById(updateList);
            testQuestionChoiceIService.updateBatch(updateList);
        } else {
            log.info(" user save qus for entity is empty");
        }
    }
}

    
    
  