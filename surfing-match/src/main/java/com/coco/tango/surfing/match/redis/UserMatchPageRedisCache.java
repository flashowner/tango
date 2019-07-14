package com.coco.tango.surfing.match.redis;

import com.coco.tango.surfing.common.redis.impl.RedisBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 记录每个用户匹配 的页数
 * redis 操作通用类
 *
 * @author ckli01
 * @date 2018/7/5
 */
@Service
public class UserMatchPageRedisCache extends RedisBaseServiceImpl<String, Long, Long> {

    /**
     * 记录每个用户匹配 redis key
     */
    private static final String USER_MATCH_PAGE_REDIS_CACHE_KEY = "user_match_page_redis_cache_key";

    @Override
    public String getKey() {
        return USER_MATCH_PAGE_REDIS_CACHE_KEY;
    }


}
