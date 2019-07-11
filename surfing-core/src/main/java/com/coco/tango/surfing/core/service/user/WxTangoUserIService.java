package com.coco.tango.surfing.core.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.dal.domain.user.WxTangoUser;

/**
 * 微信 tango 用户关联 服务
 *
 * @author ckli01
 * @date 2019-07-07
 */
public interface WxTangoUserIService extends IService<WxTangoUser> {


    /**
     * 根据 微信 用户Id 获取 微信Tango 用户关联关系
     * @param id
     * @return
     */
    WxTangoUser findByWxUserId(Long id);
}
