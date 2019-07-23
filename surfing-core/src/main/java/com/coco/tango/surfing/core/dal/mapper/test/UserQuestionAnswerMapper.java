package com.coco.tango.surfing.core.dal.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户作答
 *
 * @author ckli01
 * @date 2019-07-09
 */
@Mapper
public interface UserQuestionAnswerMapper extends BaseMapper<UserQuestionAnswer> {

    /**
     * 根据 题目Ids 获取 用户信息
     * @param qusIds
     * @return
     */
    List<Long> userQusAnswer(@Param("list") List<Long> qusIds);
}
