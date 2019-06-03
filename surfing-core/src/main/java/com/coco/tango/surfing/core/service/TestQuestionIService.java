package com.coco.tango.surfing.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.TestQuestion;

import java.util.List;

/**
 * 测试题 服务入口
 *
 * @author ckli01
 * @date 2019-06-03
 */
public interface TestQuestionIService extends IService<TestQuestion> {


    List<TestQuestion> selectAll();

}
