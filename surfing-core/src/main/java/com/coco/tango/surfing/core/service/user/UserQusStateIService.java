package com.coco.tango.surfing.core.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.dal.domain.user.UserQusState;

/**
 * 用户 题组 完成 情况
 *
 * @author ckli01
 * @date 2019-07-14
 */
public interface UserQusStateIService extends IService<UserQusState> {

    /**
     * 更新用户 测试题目完成情况
     * @param userId
     */
    boolean updateTestState(Long userId);

    /**
     * 获取 用户 系统 题目完成情况状态
     * @param id
     * @return
     */
    boolean getSysStateByUserId(Long id);
}
