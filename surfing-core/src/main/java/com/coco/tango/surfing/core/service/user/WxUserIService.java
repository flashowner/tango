package com.coco.tango.surfing.core.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.user.WxUser;

/**
 * 微信 用户服务
 *
 * @author ckli01
 * @date 2019-07-07
 */
public interface WxUserIService extends IService<WxUser> {
    /**
     * 获取 微信 用户信息
     * @param openId
     * @return
     */
    WxUser findByOpenId(String openId);
}
