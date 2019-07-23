package com.coco.tango.surfing.match.redis;

import com.coco.tango.surfing.common.redis.impl.RedisBaseServiceImpl;
import com.coco.tango.surfing.match.constants.Constant;
import org.springframework.stereotype.Service;

/**
 * 记录每个用户匹配 的页数
 * redis 操作通用类
 *
 * @author ckli01
 * @date 2018/7/5
 */
@Service
public class UserMatchPageRedisCache extends RedisBaseServiceImpl<String, Long, Integer> {


    @Override
    public String getKey() {
        return Constant.USER_MATCH_PAGE_REDIS_CACHE_KEY;
    }


}
