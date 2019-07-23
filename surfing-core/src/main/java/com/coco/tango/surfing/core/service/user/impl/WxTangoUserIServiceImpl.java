package com.coco.tango.surfing.core.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.dal.domain.user.WxTangoUser;
import com.coco.tango.surfing.core.dal.mapper.user.WxTangoUserMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.user.WxTangoUserIService;
import org.springframework.stereotype.Service;

/**
 * 微信 tango 用户 关联 服务
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Service
public class WxTangoUserIServiceImpl extends ServiceImpl<WxTangoUserMapper, WxTangoUser> implements
        WxTangoUserIService {


    @Override
    public WxTangoUser findByWxUserId(Long id) {
        return super.getOne(new LambdaQueryWrapper<WxTangoUser>().eq(WxTangoUser::getWxUserId, id)
        );
    }
}

    
    
  