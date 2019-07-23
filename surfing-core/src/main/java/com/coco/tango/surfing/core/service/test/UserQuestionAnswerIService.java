package com.coco.tango.surfing.core.service.test;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;

import java.util.List;

/**
 * 用户作答
 *
 * @author ckli01
 * @date 2019-07-09
 */
public interface UserQuestionAnswerIService extends IService<UserQuestionAnswer> {


    /**
     * 根据用户Id 题目Id 获取 答题结果
     *
     * @param userIds
     * @param sysQuesIds
     * @return
     */
    List<UserQuestionAnswer> listByUserIdsAndQuesIds(List<Long> userIds, List<Long> sysQuesIds);

    /**
     * 逻辑删除 旧答题记录
     *
     * @param id
     * @param quesIds
     */
    boolean logicDeleteByUserAndQuesId(Long id, List<Long> quesIds);

    /**
     * 根据 题目Ids 获取 用户信息
     *
     * @param qusIds
     * @return
     */
    List<Long> userQusAnswer(List<Long> qusIds);


    /**
     * 根据 题目Id 用户 编码 获取 答题记录
     *
     * @param qusIds
     * @param userCode
     * @return
     */
    List<UserQuestionAnswer> userQusAnswer(List<Long> qusIds, String userCode);
}
