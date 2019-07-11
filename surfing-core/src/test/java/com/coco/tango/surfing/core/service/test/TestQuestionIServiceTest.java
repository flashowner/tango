package com.coco.tango.surfing.core.service.test;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试题 UT
 *
 * @author ckli01
 * @date 2019-06-03
 */
public class TestQuestionIServiceTest extends UTBase {

    @Autowired
    private TestQuestionIService testQuestionService;

    @Autowired
    private TestQuestionChoiceIService choiceIService;

    @Test
    public void insertTest() {

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setContent("你是不是逗比2？");
        testQuestion.setOrderNum(1);

        boolean b = testQuestionService.save(testQuestion);

        List<TestQuestionChoice> list = new ArrayList<>();

        TestQuestionChoice testQuestionChioce = new TestQuestionChoice();

        testQuestionChioce.setContent("不是");
        testQuestionChioce.setOrderChar("A");
        testQuestionChioce.setOrderNum(1);
        testQuestionChioce.setQuestionId(testQuestion.getId());
        list.add(testQuestionChioce);

        testQuestionChioce = new TestQuestionChoice();

        testQuestionChioce.setContent("是");
        testQuestionChioce.setOrderChar("B");
        testQuestionChioce.setOrderNum(2);
        testQuestionChioce.setQuestionId(testQuestion.getId());
        list.add(testQuestionChioce);


        boolean c = choiceIService.saveBatch(list);

        Assert.assertTrue(c);
    }



    @Test
    public void systemQuestionTest() {
        List<TestQuestion> list = testQuestionService.systemQuestion();

        Assert.assertNotNull(list);
    }
}

    
    
  