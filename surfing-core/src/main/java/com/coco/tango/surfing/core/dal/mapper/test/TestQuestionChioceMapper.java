package com.coco.tango.surfing.core.dal.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChioce;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 测试题目 DAO
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Mapper
public interface TestQuestionChioceMapper extends BaseMapper<TestQuestionChioce> {
}
