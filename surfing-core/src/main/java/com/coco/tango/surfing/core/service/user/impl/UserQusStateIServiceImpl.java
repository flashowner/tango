package com.coco.tango.surfing.core.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.user.UserQusState;
import com.coco.tango.surfing.core.dal.mapper.user.UserQusStateMapper;
import com.coco.tango.surfing.core.enums.CreateQuestionTypeEnum;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.user.UserQusStateIService;
import org.springframework.stereotype.Service;

/**
 * 用户题组完成情况
 *
 * @author ckli01
 * @date 2019-07-14
 */
@Service
public class UserQusStateIServiceImpl extends ServiceImpl<UserQusStateMapper, UserQusState> implements UserQusStateIService {


    @Override
    public boolean updateTestState(Long userId) {
        UserQusState userQusState = new UserQusState();
        userQusState.setQusGroup(CreateQuestionTypeEnum.SYSTEM.getType());
        userQusState.setState(true);
        userQusState.setUserId(userId);
        return super.save(userQusState);
    }

    @Override
    public boolean getSysStateByUserId(Long id) {
        UserQusState userQusState = super.lambdaQuery()
                .eq(UserQusState::getUserId, id)
                .eq(UserQusState::getQusGroup, CreateQuestionTypeEnum.SYSTEM.getType())
                .eq(UserQusState::getDeleted, YesOrNoEnum.NO.getValue())
                .one();

        return userQusState != null && userQusState.isState();
    }
}

    
    
  