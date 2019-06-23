package com.coco.tango.surfing.core.service.test.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestion;
import com.coco.tango.surfing.core.dal.mapper.test.TestQuestionMapper;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试题 实现类
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Service
public class TestQuestionIServiceImpl extends ServiceImpl<TestQuestionMapper, TestQuestion>
        implements TestQuestionIService {

    @Override
    public List<TestQuestion> selectAll() {
        return super.getBaseMapper().selectAll();
    }
}

    
    
  