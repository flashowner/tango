package com.coco.tango.surfing.core.dal.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试题目 DAO
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Mapper
public interface TestQuestionChoiceMapper extends BaseMapper<TestQuestionChoice> {
}
