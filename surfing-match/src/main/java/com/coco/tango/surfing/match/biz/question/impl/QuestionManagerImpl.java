package com.coco.tango.surfing.match.biz.question.impl;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.common.filter.TangoFilter;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import com.coco.tango.surfing.core.service.test.UserQuestionAnswerIService;
import com.coco.tango.surfing.match.bean.vo.UserQuestionAnswerVO;
import com.coco.tango.surfing.match.biz.question.QuestionManager;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 题目服务 实现类
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Service
public class QuestionManagerImpl implements QuestionManager {


    @Autowired
    private TestQuestionIService testQuestionIService;

    @Autowired
    private UserQuestionAnswerIService userQuestionAnswerIService;


    @Override
    public List<TestQuestion> systemQuestion() {
        return testQuestionIService.systemQuestion();
    }


    @Override
    public Boolean userAnswer(List<UserQuestionAnswerVO> list) throws Exception {
        // 获取 用户信息
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();

        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("请提交答题记录");
        }

        List<UserQuestionAnswer> answers = Lists.newArrayList();
        for (UserQuestionAnswerVO userQuestionAnswerVO : list) {
            UserQuestionAnswer answer = new UserQuestionAnswer();
            answer.setAnswer(userQuestionAnswerVO.getAnswer());
            answer.setQuestionId(userQuestionAnswerVO.getQuestionId());
            answer.setUserId(tangoUserDTO.getId());
            answer.setCreateUser(tangoUserDTO.getCode());
            answer.setUpdateUser(tangoUserDTO.getCode());
        }


        return userQuestionAnswerIService.saveBatch(answers);
    }
}

    
    
  