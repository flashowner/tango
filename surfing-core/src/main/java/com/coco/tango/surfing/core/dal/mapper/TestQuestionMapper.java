package com.coco.tango.surfing.core.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.TestQuestion;

import java.util.List;

/**
 * 测试题 DAO
 *
 * @author ckli01
 * @date 2019-06-03
 */
public interface TestQuestionMapper extends BaseMapper<TestQuestion> {

    List<TestQuestion> selectAll();
}
