package com.coco.tango.surfing.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.TestQuestion;
import com.coco.tango.surfing.core.dal.mapper.TestQuestionMapper;
import com.coco.tango.surfing.core.service.TestQuestionIService;
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

    
    
  