package com.coco.tango.surfing.core.service.test.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.test.TestQuestionChoice;
import com.coco.tango.surfing.core.dal.mapper.test.TestQuestionChoiceMapper;
import com.coco.tango.surfing.core.service.test.TestQuestionChoiceIService;
import org.springframework.stereotype.Service;

/**
 * 测试题 选项 实现类
 *
 * @author ckli01
 * @date 2019-06-03
 */
@Service
public class TestQuestionChoiceIServiceImpl extends ServiceImpl<TestQuestionChoiceMapper, TestQuestionChoice>
        implements TestQuestionChoiceIService {
}

    
    
  