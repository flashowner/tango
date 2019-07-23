package com.coco.tango.surfing.core.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.user.WxUser;
import com.coco.tango.surfing.core.dal.mapper.user.WxUserMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.user.WxUserIService;
import org.springframework.stereotype.Service;

/**
 * 微信用户 服务 实现
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Service
public class WxUserIServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserIService {


    @Override
    public WxUser findByOpenId(String openId) {
        return super.getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenId, openId)
        );
    }


}

    
    
  