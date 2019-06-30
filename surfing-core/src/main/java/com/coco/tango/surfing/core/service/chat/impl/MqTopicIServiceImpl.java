package com.coco.tango.surfing.core.service.chat.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.core.dal.domain.chat.MqTopic;
import com.coco.tango.surfing.core.dal.mapper.chat.MqTopicMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.chat.MqTopicIService;
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

    
    
  