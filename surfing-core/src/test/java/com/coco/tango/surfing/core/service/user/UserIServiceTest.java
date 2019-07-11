package com.coco.tango.surfing.core.service.user;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户
 *
 * @author ckli01
 * @date 2019-06-27
 */
public class UserIServiceTest extends UTBase {

    @Autowired
    private TangoUserIService userIService;


    @Test
    public void saveTest() {
        TangoUser user = new TangoUser();
//        user.setOnLineTime(new Date());
        userIService.save(user);


        System.out.println(1);

    }
}

    
    
  