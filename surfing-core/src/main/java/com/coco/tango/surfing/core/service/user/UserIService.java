package com.coco.tango.surfing.core.service.user;

import com.coco.tango.surfing.core.dal.domain.user.User;

/**
 * 用户
 *
 * @author ckli01
 * @date 2019-06-27
 */
public interface UserIService {


    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    User save(User user);


    /**
     * 根据 code 获取 用户信息
     * @param code
     * @return
     */
    User findByCode(String code) throws Exception;



}
