package com.coco.tango.surfing.core.dal.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.user.UserQusState;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户题组 完成情况
 *
 * @author ckli01
 * @date 2019-07-14
 */
@Mapper
public interface UserQusStateMapper extends BaseMapper<UserQusState> {
}
