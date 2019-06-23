package com.coco.tango.surfing.core.service.topic.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.topic.MqTopic;
import com.coco.tango.surfing.core.dal.mapper.topic.MqTopicMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.topic.MqTopicIService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mq Topic tag 实现类
 *
 * @author ckli01
 * @date 2019-06-17
 */
@Service
public class MqTopicIServiceImpl extends ServiceImpl<MqTopicMapper, MqTopic>
        implements MqTopicIService {

    @Override
    public List<MqTopic> all() {
        return super.list(new LambdaQueryWrapper<MqTopic>().eq(MqTopic::getDeleted, YesOrNoEnum.NO.getValue()));
    }
}

    
    
  