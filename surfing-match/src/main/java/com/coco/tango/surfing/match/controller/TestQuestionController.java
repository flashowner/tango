package com.coco.tango.surfing.match.controller;

import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.controller.AbstractBaseController;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.match.bean.vo.UserQuestionAnswerVO;
import com.coco.tango.surfing.match.biz.question.QuestionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试题目
 *
 * @author ckli01
 * @date 2019-07-09
 */
@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractBaseController {


    @Autowired
    private QuestionManager questionManager;


    /**
     * 获取 系统测试题目
     * @return
     */
    @GetMapping
    public HttpRestResult<List<TestQuestion>> systemQuestion() {
        return responseOK(questionManager.systemQuestion());
    }


    /**
     * 获取 系统测试题目
     * @return
     */
    @PostMapping
    public HttpRestResult<Boolean> userAnswer(@RequestBody List<UserQuestionAnswerVO> list) throws Exception {
        return responseOK(questionManager.userAnswer(list));
    }





}




















  