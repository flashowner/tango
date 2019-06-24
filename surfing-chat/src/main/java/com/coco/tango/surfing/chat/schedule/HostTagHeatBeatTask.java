package com.coco.tango.surfing.chat.schedule;

import com.coco.tango.surfing.chat.bootstrap.init.DistributeTopicInitHandler;
import com.coco.tango.surfing.chat.cache.redis.HostTopicCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 维护本机 是否在线 心跳
 *
 * @author ckli01
 * @date 2019-06-19
 */
@Component
@Slf4j
public class HostTagHeatBeatTask {

    @Autowired
    private HostTopicCache hostTopicCache;

    @Scheduled(cron = "*/10 * * * * *")
    public void heatBeat() {
        hostTopicCache.expireHostTagKey();
        log.debug("hostTagCache heart beat for key {} success...", DistributeTopicInitHandler.getHostTopicCacheKey());
    }


}

    
    
  