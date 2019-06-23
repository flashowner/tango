package com.coco.tango.surfing.core.dal.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息 Mapper
 *
 * @author ckli01
 * @date 2019-06-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
