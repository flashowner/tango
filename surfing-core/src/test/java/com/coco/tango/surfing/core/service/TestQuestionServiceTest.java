package com.coco.tango.surfing.core.service;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.TestQuestionChioce;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试题 UT
 *
 * @author ckli01
 * @date 2019-06-03
 */
public class TestQuestionServiceTest extends UTBase {

    @Autowired
    private TestQuestionIService testQuestionService;

    @Autowired
    private TestQuestionChioceIService chioceIService;

    @Test
    public void insertTest() {

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setContent("你是不是逗比2？");
        testQuestion.setOrderNum(1);

        boolean b = testQuestionService.save(testQuestion);


        List<TestQuestionChioce> list = new ArrayList<>();

        TestQuestionChioce testQuestionChioce = new TestQuestionChioce();

        testQuestionChioce.setContent("不是");
        testQuestionChioce.setOrderChar("A");
        testQuestionChioce.setOrderNum(1);
        testQuestionChioce.setQuestionId(testQuestion.getId());
        list.add(testQuestionChioce);


        testQuestionChioce = new TestQuestionChioce();

        testQuestionChioce.setContent("是");
        testQuestionChioce.setOrderChar("B");
        testQuestionChioce.setOrderNum(2);
        testQuestionChioce.setQuestionId(testQuestion.getId());
        list.add(testQuestionChioce);


        boolean c = chioceIService.saveBatch(list);

        Assert.assertTrue(c);
    }


    @Test
    public void searchTest() {
        List<TestQuestion> list = testQuestionService.selectAll();
        Assert.assertNotNull(list);
    }


}

    
    
  