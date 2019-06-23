package com.coco.tango.surfing.chat.bootstrap.topic;

import com.coco.tango.surfing.chat.cache.redis.HostTopicCache;
import com.coco.tango.surfing.chat.constant.MqConstants;
import com.coco.tango.surfing.chat.constant.RedisConstant;
import com.coco.tango.surfing.chat.mq.consumer.support.ListenerContainerConfigurationIniter;
import com.coco.tango.surfing.chat.mq.consumer.support.RocketMQMessageListenerConfigIniter;
import com.coco.tango.surfing.common.redis.impl.RedisDistributedLock;
import com.coco.tango.surfing.common.utils.IpUtils;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import com.coco.tango.surfing.core.dal.domain.topic.MqTopic;
import com.coco.tango.surfing.core.service.topic.MqTopicIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * Mq topic 调用
 *
 * @author ckli01
 * @date 2019-06-14
 */
@Component
@Slf4j
public class DistributeTopicInitHandler implements InitializingBean {


    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private MqTopicIService mqTopicIService;

    @Autowired
    private HostTopicCache hostTopicCache;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private ListenerContainerConfigurationIniter listenerContainerConfigurationIniter;

    /**
     * 本机占用TOPIC
     */
    public static String TANGO_SURFING_TOPIC = null;

    /**
     * 本机占用 TOPIC 缓存 key
     */
    public static String TANGO_SURFING_TOPIC_CACHE_KEY = null;


    @PostConstruct
    public void init() {
        log.info("TopicInitHandler init preparing....");
        if (redisDistributedLock.lock(RedisConstant.TOPIC_TAG_INIT_KEY)) {
            log.info("TopicInitHandler start init...");
            List<MqTopic> list = mqTopicIService.all();
            if (!CollectionUtils.isEmpty(list)) {
                for (MqTopic mqTopic : list) {
                    String key = topicCacheKey(mqTopic.getTopic());
                    if (StringUtils.isEmpty(key)) {
                        continue;
                    }
                    // 判断tag 是否有机器已经使用
                    if (hostTopicCache.isExist(key)) {
                        continue;
                    } else {
                        // 没使用就本机占用
                        mqTopic.setLastHostIp(IpUtils.getHost());
                        mqTopicIService.updateById(mqTopic);
                        TANGO_SURFING_TOPIC = mqTopic.getTopic();
                        hostTopicCache.expireHostTagKey();
                        log.info("TopicInitHandler current host will use topic for {} ", TANGO_SURFING_TOPIC);
                        break;
                    }
                }
            }

            // 判断本机是否有可以使用的TAG
            if (StringUtils.isEmpty(TANGO_SURFING_TOPIC)) {
                // 生成新的TAG
                MqTopic mqTopic = new MqTopic();
                mqTopic.setLastHostIp(IpUtils.getHost());

                mqTopic.setTopic(MqConstants.MQ_TOPIC_KEY_PREFIX + uid());

                mqTopic.setLastHostIp(IpUtils.getHost());
                mqTopicIService.save(mqTopic);
                TANGO_SURFING_TOPIC = mqTopic.getTopic();
                hostTopicCache.expireHostTagKey();
                log.info("TopicInitHandler current host will use topic for {} ", TANGO_SURFING_TOPIC);
            }
            log.info("TopicInitHandler init success");
            // 维护本机占用TAG
            // todo 用户在哪个机器上 key hash value  hash -》 userCode value tag
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("TopicInitHandler init mq consumer for topic : {}", TANGO_SURFING_TOPIC);
//        listenerContainerConfigurationIniter.registerContainer(
//                RocketMQMessageListenerConfigIniter.getRocketMQMessageListenerConfig());
        log.info("TopicInitHandler init mq consumer for topic : {} success", TANGO_SURFING_TOPIC);
    }


    /**
     * 本机占用 TAG 缓存 key
     *
     * @return
     */
    public static String getHostTopicCacheKey() {
        if (StringUtils.isEmpty(TANGO_SURFING_TOPIC_CACHE_KEY)) {
            TANGO_SURFING_TOPIC_CACHE_KEY = topicCacheKey(TANGO_SURFING_TOPIC);
        }
        return TANGO_SURFING_TOPIC_CACHE_KEY;
    }


    /**
     * TOPIC 缓存 key
     *
     * @param
     * @return
     */
    private static String topicCacheKey(String topic) {
        if (!StringUtils.isEmpty(topic)) {
            return RedisConstant.REDIS_MQ_TOPIC_HOST_USE_PREFIX + topic;
        } else {
            return null;
        }
    }


    /**
     * UID
     *
     * @return
     */
    private String uid() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }


}



