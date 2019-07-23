package com.coco.tango.surfing.core.dal.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试题目 DAO
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Mapper
public interface TestQuestionChoiceMapper extends BaseMapper<TestQuestionChoice> {
    /**
     * 根据 题目 Ids 获取 答题人
     *
     * @param qusIds
     * @return
     */
    List<Long> userQusAnswer(@Param("list") List<Long> qusIds);
}
