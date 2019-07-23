package com.coco.tango.surfing.match.redis;

import com.coco.tango.surfing.common.redis.impl.RedisBaseServiceImpl;
import com.coco.tango.surfing.match.bean.vo.MatchPeopleVO;
import com.coco.tango.surfing.match.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户已匹配用户 待分配
 *
 * @author ckli01
 * @date 2019-07-21
 */
@Component
@Slf4j
public class UserMatchOtherRedisCache extends RedisBaseServiceImpl<String, String, Object> {


    /**
     * 保存 用户 已匹配用户
     *
     * @param userId
     * @param users
     */
    public void pushAll(Long userId, List<MatchPeopleVO> users) {

        if (!CollectionUtils.isEmpty(users)) {
            String key = key(userId);
            super.getRedisTemplate().opsForList().leftPushAll(key, users);
            super.getRedisTemplate().expire(key, 7, TimeUnit.DAYS);
        } else {
            log.info("userId : {} pushAll user is empty", userId);
        }


    }


    /**
     * 获取 一个 已匹配的用户
     *
     * @param userId
     * @return
     */
    public MatchPeopleVO popOne(Long userId) {
        return (MatchPeopleVO) super.getRedisTemplate().opsForList().rightPop(key(userId));
    }


    private String key(Long userId) {
        return Constant.USER_MATCH_OTHER_PREFIEX_CACHE_KEY + userId;
    }


}

    
    
  