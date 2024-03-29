package com.coco.tango.surfing.core.dal.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Tango 用户  Mapper
 *
 * @author ckli01
 * @date 2019-06-06
 */
@Mapper
public interface TangoUserMapper extends BaseMapper<TangoUser> {


    /**
     * 获取 最大 code
     * @param prefix
     * @return
     */
    String getLastCode(@Param("prefix") String prefix);

    /**
     * 分页获取 用户Id
     *
     * @param startRow
     * @param size
     * @return
     */
    List<TangoUser> listByPages(@Param("startRow")Integer startRow,@Param("size") int size);
}
