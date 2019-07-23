package com.coco.tango.surfing.match.biz.question.impl;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.common.filter.TangoFilter;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.service.test.TestQuestionChoiceIService;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import com.coco.tango.surfing.core.service.test.UserQuestionAnswerIService;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import com.coco.tango.surfing.core.service.user.UserQusStateIService;
import com.coco.tango.surfing.match.bean.vo.UserQuestionAnswerVO;
import com.coco.tango.surfing.match.biz.question.QuestionManager;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private TestQuestionChoiceIService testQuestionChoiceIService;

    @Autowired
    private UserQuestionAnswerIService userQuestionAnswerIService;

    @Autowired
    private TangoUserIService tangoUserIService;

    @Autowired
    private UserQusStateIService qusStateIService;


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


        List<Long> quesIds = Lists.newArrayList();
        List<UserQuestionAnswer> answers = Lists.newArrayList();
        for (UserQuestionAnswerVO userQuestionAnswerVO : list) {
            UserQuestionAnswer answer = new UserQuestionAnswer();
            answer.setAnswer(userQuestionAnswerVO.getAnswer());
            answer.setQuestionId(userQuestionAnswerVO.getQuestionId());
            answer.setUserId(tangoUserDTO.getId());
            answer.setCreateUser(tangoUserDTO.getCode());
            answer.setUpdateUser(tangoUserDTO.getCode());
            answers.add(answer);
            quesIds.add(userQuestionAnswerVO.getQuestionId());
        }


        // 逻辑删除 旧答题记录
        userQuestionAnswerIService.logicDeleteByUserAndQuesId(tangoUserDTO.getId(), quesIds);

        // 获取 系统题目Ids
        List<Long> sysQuesIds = testQuestionIService.systemQuestionIds();

        if (sysQuesIds.size() == quesIds.size()) {
            // todo 简单的判断是系统题目
            // 更新 用户 答题状态
            qusStateIService.updateTestState(tangoUserDTO.getId());
        }

        return userQuestionAnswerIService.saveBatch(answers);
    }

    @Override
    public List<UserQuestionAnswerVO> historyAnswer() {
        // 获取 用户信息
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();

        // 获取 系统题目Ids
        List<Long> sysQuesIds = testQuestionIService.systemQuestionIds();

        // 获取 用户 系统 答题记录
        List<UserQuestionAnswer> userAnswers = userQuestionAnswerIService.listByUserIdsAndQuesIds(Lists.newArrayList(tangoUserDTO.getId()), sysQuesIds);


        return Optional.ofNullable(userAnswers)
                .orElse(Lists.newArrayList())
                .stream()
                .map(t -> {
                    UserQuestionAnswerVO userQuestionAnswerVO = new UserQuestionAnswerVO();
                    userQuestionAnswerVO.setAnswer(t.getAnswer());
                    userQuestionAnswerVO.setQuestionId(t.getQuestionId());
                    userQuestionAnswerVO.setScore(t.getScore());
                    return userQuestionAnswerVO;
                })
                .collect(Collectors.toList());
    }


    /**
     * 根据 用户编码 获取 用户设置的题目
     *
     * @param userCode
     * @return
     */
    @Override
    public List<TestQuestion> userQus(String userCode) {
        return testQuestionIService.userQus(userCode);
    }


    /**
     * 用户自定义设置题目
     *
     * @param userQus
     * @return
     */
    @Override
    @Transactional
    public Boolean userQusSave(List<TestQuestion> userQus) throws Exception {

        if (CollectionUtils.isEmpty(userQus)) {
            throw new ServiceException("您没有设置题目哦，亲!");
        }
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();

        List<TestQuestion> addList = Lists.newArrayList();
        List<TestQuestion> updateList = Lists.newArrayList();

        userQus.forEach(testQuestion -> {
            // 用户自定义题目
            testQuestion.setCreateType(1);
            if (null != testQuestion.getId()) {
                testQuestion.setUpdateUser(tangoUserDTO.getCode());
                updateList.add(testQuestion);
            } else {
                testQuestion.setCreateUser(tangoUserDTO.getCode());
                addList.add(testQuestion);
            }
        });

        testQuestionIService.saveBatch(addList);
        testQuestionIService.updateBatch(updateList);

        return true;
    }


    /**
     * 根据 用户 获取 用户自定义答题信息
     *
     * @param userCode
     * @return
     */
    @Override
    public List<UserQuestionAnswer> userQusAnswer(String userCode) throws Exception {
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();
        List<TestQuestion> testQuestions = this.userQus(tangoUserDTO.getCode());
        if (CollectionUtils.isEmpty(testQuestions)) {
            throw new ServiceException("您没有设置题目哦，亲!");
        }

        List<Long> qusIds = testQuestions.stream().map(TestQuestion::getId).collect(Collectors.toList());

        return userQuestionAnswerIService.userQusAnswer(qusIds, userCode);
    }


    @Override
    public List<TangoUserDTO> userQusAnswerUsers() throws Exception {
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();
        List<TestQuestion> testQuestions = this.userQus(tangoUserDTO.getCode());
        if (CollectionUtils.isEmpty(testQuestions)) {
            throw new ServiceException("暂未收到申请哦，亲!");
        }
        List<Long> qusIds = testQuestions.stream().map(TestQuestion::getId).collect(Collectors.toList());
        List<Long> userIds = userQuestionAnswerIService.userQusAnswer(qusIds);

        Collection<TangoUser> users = tangoUserIService.listByIds(userIds);

        if (CollectionUtils.isEmpty(users)) {
            throw new ServiceException("暂未收到申请哦，亲!");
        }

        return users.stream().map(tangoUser -> {
            TangoUserDTO userDTO = new TangoUserDTO();
            userDTO.setAvatarUrl(tangoUser.getAvatarUrl());
            userDTO.setCode(tangoUser.getCode());
            userDTO.setName(tangoUser.getName());
            userDTO.setSex(tangoUser.getSex());
            return userDTO;
        }).collect(Collectors.toList());
    }
}

    
    
  