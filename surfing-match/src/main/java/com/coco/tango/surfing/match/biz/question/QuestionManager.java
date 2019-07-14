package com.coco.tango.surfing.match.biz.question;

import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
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
     * @return
     */
    List<TestQuestion> systemQuestion();

    /**
     * 保存用户题目作答
     * @param list
     * @return
     */
    Boolean userAnswer(List<UserQuestionAnswerVO> list) throws Exception;

    /**
     * 获取 用户 历史 系统 答题信息
     * @return
     */
    List<UserQuestionAnswerVO> historyAnswer();
}
