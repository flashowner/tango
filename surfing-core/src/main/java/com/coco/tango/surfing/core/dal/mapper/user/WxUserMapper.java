package com.coco.tango.surfing.core.dal.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.user.WxUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信 用户 Mapper
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {
}
