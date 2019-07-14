package com.coco.tango.surfing.core.service.user;

import com.coco.tango.surfing.core.UTBase;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ckli01
 * @date 2019-07-14
 */
public class UserQusStateIServiceTest extends UTBase {


    @Autowired
    private UserQusStateIService qusStateIService;


    @Test
    public void getSysStateByUserIdTest() {


        boolean result = qusStateIService.getSysStateByUserId(1L);


        System.out.println(result);

    }
}

    
    
  