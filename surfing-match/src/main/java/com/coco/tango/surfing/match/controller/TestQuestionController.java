package com.coco.tango.surfing.match.controller;

import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.controller.AbstractBaseController;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
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
     *
     * @return
     */
    @GetMapping
    public HttpRestResult<List<TestQuestion>> systemQuestion() {
        return responseOK(questionManager.systemQuestion());
    }


    /**
     * 用户答题 测试题目
     *
     * @return
     */
    @PostMapping
    public HttpRestResult<Boolean> userAnswer(@RequestBody List<UserQuestionAnswerVO> list) throws Exception {
        return responseOK(questionManager.userAnswer(list));
    }


    /**
     * 用户答题 已回答的 测试题目
     *
     * @return
     */
    @GetMapping("/history/answer")
    public HttpRestResult<List<UserQuestionAnswerVO>> historyAnswer() throws Exception {
        return responseOK(questionManager.historyAnswer());
    }


    /**
     * 根据 用户 获取 用户 设置的题目
     *
     * @param userCode
     * @return
     */
    @GetMapping("/userQus/{userCode}")
    public HttpRestResult<List<TestQuestion>> userQus(@PathVariable("userCode") String userCode) {
        return responseOK(questionManager.userQus(userCode));
    }


    /**
     * 用户自定义设置题目
     *
     * @param userQus
     * @return
     */
    @PostMapping("/userQus/save")
    public HttpRestResult<Boolean> userQusSave(@RequestBody List<TestQuestion> userQus) throws Exception {
        return responseOK(questionManager.userQusSave(userQus));
    }

    /**
     * 根据用户 获取 他的 答题信息
     *
     * @param userCode
     * @return
     */
    @GetMapping("/userQus/answer/{userCode}")
    public HttpRestResult<List<UserQuestionAnswer>> userQusAnswer(@PathVariable("userCode") String userCode) throws Exception {
        return responseOK(questionManager.userQusAnswer(userCode));
    }



    /**
     * 获取 已回答问题的用户信息
     *
     * @return
     */
    @GetMapping("/userQus/answer/users")
    public HttpRestResult<List<TangoUserDTO>> userQusAnswerUsers() throws Exception {
        return responseOK(questionManager.userQusAnswerUsers());
    }



}




















  