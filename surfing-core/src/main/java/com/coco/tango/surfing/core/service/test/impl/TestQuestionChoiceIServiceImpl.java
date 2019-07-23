package com.coco.tango.surfing.core.service.test.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import com.coco.tango.surfing.core.dal.mapper.test.TestQuestionChoiceMapper;
import com.coco.tango.surfing.core.enums.TestQuestionEnum;
import com.coco.tango.surfing.core.service.test.TestQuestionChoiceIService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 测试题 选项 实现类
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Service
public class TestQuestionChoiceIServiceImpl extends ServiceImpl<TestQuestionChoiceMapper, TestQuestionChoice>
        implements TestQuestionChoiceIService {


    @Override
    @Transactional
    public void saveTestQusChoice(Collection<TestQuestion> entityList) {
        List<TestQuestionChoice> list = Lists.newArrayList();

        entityList.stream()
                .filter(testQuestion ->
                        testQuestion.getQuestionType().equals(TestQuestionEnum.CHOICE.getType())
                                && !CollectionUtils.isEmpty(testQuestion.getTestQuestionChoices())
                ).forEach(testQuestion -> {
            testQuestion.getTestQuestionChoices().forEach(testQuestionChoice -> {

                testQuestionChoice.setCreateUser(testQuestion.getCreateUser());
                testQuestionChoice.setQuestionId(testQuestion.getId());

                list.add(testQuestionChoice);
            });

        });
        super.saveBatch(list);
    }


    @Override
    @Transactional
    public void updateBatch(List<TestQuestion> updateList) {
        List<TestQuestionChoice> list = Lists.newArrayList();


        List<Long> quesId = Lists.newArrayList();
        updateList.forEach(testQuestion -> {
            quesId.add(testQuestion.getId());

            if (!CollectionUtils.isEmpty(testQuestion.getTestQuestionChoices())) {

                testQuestion.getTestQuestionChoices().forEach(testQuestionChoice -> {
                    testQuestionChoice.setId(null);
                    testQuestionChoice.setCreateUser(testQuestion.getUpdateUser());
                    testQuestionChoice.setQuestionId(testQuestion.getId());

                    list.add(testQuestionChoice);
                });
            }
        });

        super.remove(new LambdaQueryWrapper<TestQuestionChoice>()
                .in(TestQuestionChoice::getQuestionId, quesId));

        super.saveBatch(list);


    }



}

    
    
  