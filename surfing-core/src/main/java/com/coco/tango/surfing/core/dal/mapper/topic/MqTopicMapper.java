package com.coco.tango.surfing.core.dal.mapper.topic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.tango.surfing.core.dal.domain.topic.MqTopic;
import org.apache.ibatis.annotations.Mapper;

/**
 * topic tag DAO
 *
 * @author ckli01
 * @date 2019-06-17
 */
@Mapper
public interface MqTopicMapper extends BaseMapper<MqTopic> {
}
