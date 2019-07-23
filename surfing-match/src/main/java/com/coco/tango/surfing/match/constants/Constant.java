package com.coco.tango.surfing.match.constants;

/**
 * 常量
 *
 * @author ckli01
 * @date 2019-07-11
 */
public class Constant {


    /**
     * 一次查询最多人数
     */
    public static final int MAX_MATCH_PAGE_SIZE = 50;


    /**
     * 每道题 分值
     */
    public static final double MAX_SCORE_PER_QUESTION = 5d;

    /**
     * 匹配度 最低值
     * todo
     */
    public static final double MIN_MATCH_DEGREE = 10d;




    /**
     * 记录每个用户匹配 redis key
     */
    public static final String USER_MATCH_PAGE_REDIS_CACHE_KEY = "user_match_page_redis_cache_key";




    /**
     * 记录每个用户匹配 的用户
     */
    public static final String USER_MATCH_OTHER_PREFIEX_CACHE_KEY = "user_match_other_prefix_";




}

    
    
  