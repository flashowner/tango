package com.coco.tango.surfing.core.dal.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 测试题 DAO
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Mapper
public interface TestQuestionMapper extends BaseMapper<TestQuestion> {

    /**
     * 根据 查询 条件 查询
     * @param testQuestion
     * @return
     */
    List<TestQuestion> selectAll(TestQuestion testQuestion);

}
