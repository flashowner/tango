package com.coco.tango.surfing.core.dal.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.user.WxTangoUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信 tango用户 关系表
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Mapper
public interface WxTangoUserMapper extends BaseMapper<WxTangoUser> {
}
