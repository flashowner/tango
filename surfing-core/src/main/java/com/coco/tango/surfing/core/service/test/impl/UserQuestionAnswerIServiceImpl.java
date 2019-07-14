package com.coco.tango.surfing.core.service.test.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.dal.mapper.test.UserQuestionAnswerMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.test.UserQuestionAnswerIService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户作答
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Service
public class UserQuestionAnswerIServiceImpl extends ServiceImpl<UserQuestionAnswerMapper, UserQuestionAnswer>
        implements UserQuestionAnswerIService {


    @Override
    public List<UserQuestionAnswer> listByUserIdsAndQuesIds(List<Long> userIds, List<Long> sysQuesIds) {
        return super.list(new LambdaQueryWrapper<UserQuestionAnswer>()
                .in(UserQuestionAnswer::getUserId, userIds)
                .in(UserQuestionAnswer::getQuestionId, sysQuesIds)
                .eq(UserQuestionAnswer::getDeleted, YesOrNoEnum.NO.getValue()));
    }


    @Override
    public boolean logicDeleteByUserAndQuesId(Long id, List<Long> quesIds) {

        UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer();
        userQuestionAnswer.setDeleted(YesOrNoEnum.YES.getValue());

        return super.update(userQuestionAnswer, new LambdaQueryWrapper<UserQuestionAnswer>()
                .eq(UserQuestionAnswer::getUserId, id)
                .in(UserQuestionAnswer::getQuestionId, quesIds)
                .eq(UserQuestionAnswer::getDeleted, YesOrNoEnum.NO.getValue()));
    }
}

    






  