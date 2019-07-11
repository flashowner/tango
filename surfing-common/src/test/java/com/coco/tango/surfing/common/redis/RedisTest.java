package com.coco.tango.surfing.common.redis;

import com.coco.tango.surfing.common.UTBase;
import com.coco.tango.surfing.common.bean.page.Page;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author ckli01
 * @date 2019-07-10
 */
public class RedisTest extends UTBase {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void setTest() {
        Page page = new Page();


        redisTemplate.opsForValue().set("heihei", page, 10L, TimeUnit.MINUTES);


        page = (Page) redisTemplate.opsForValue().get("heihei");


        System.out.println(1);
    }

    @Test
    public void expireTest() {

        Boolean b=redisTemplate.expire("heiehi",10L,TimeUnit.MINUTES);

        if(b!=null && !b){
            redisTemplate.opsForValue().set("heiehi","",10L,TimeUnit.MINUTES);
            b=redisTemplate.expire("heiehi",10L,TimeUnit.MINUTES);

            Assert.assertNotNull(b);
            Assert.assertTrue(b);
        }

        Assert.assertNotNull(b);

        Assert.assertTrue(b);


    }
}

    
    
  