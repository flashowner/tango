package com.coco.tango.surfing.match.biz.question;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.match.bean.vo.UserQuestionAnswerVO;

import java.util.List;

/**
 * 题目服务接口
 *
 * @author ckli01
 * @date 2019-07-09
 */
public interface QuestionManager {


    /**
     * 获取 系统 配置 题目 信息
     *
     * @return
     */
    List<TestQuestion> systemQuestion();

    /**
     * 保存用户题目作答
     *
     * @param list
     * @return
     */
    Boolean userAnswer(List<UserQuestionAnswerVO> list) throws Exception;

    /**
     * 获取 用户 历史 系统 答题信息
     *
     * @return
     */
    List<UserQuestionAnswerVO> historyAnswer();

    /**
     * 根据 用户编码 获取 用户设置的题目
     *
     * @param userCode
     * @return
     */
    List<TestQuestion> userQus(String userCode);

    /**
     * 用户自定义设置题目
     *
     * @param userQus
     * @return
     */
    Boolean userQusSave(List<TestQuestion> userQus) throws Exception;

    /**
     * 根据 用户 获取 用户自定义答题信息
     *
     * @param userCode
     * @return
     */
    List<UserQuestionAnswer> userQusAnswer(String userCode) throws Exception;

    /**
     * 获取 已回答问题的用户信息
     *
     * @return
     */
    List<TangoUserDTO> userQusAnswerUsers() throws Exception;
}
