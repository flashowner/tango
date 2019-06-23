package com.coco.tango.surfing.core.service.topic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.topic.MqTopic;

import java.util.List;

/**
 * Mq Topic tag
 *
 * @author ckli01
 * @date 2019-06-17
 */
public interface MqTopicIService extends IService<MqTopic> {
    /**
     * 获取 所有 正常Tag
     * @return
     */
    List<MqTopic> all();
}
