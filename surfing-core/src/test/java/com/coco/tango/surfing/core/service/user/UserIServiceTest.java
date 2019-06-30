package com.coco.tango.surfing.core.service.user;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 用户
 *
 * @author ckli01
 * @date 2019-06-27
 */
public class UserIServiceTest extends UTBase {

    @Autowired
    private UserIService userIService;


    @Test
    public void saveTest() {
        User user = new User();
        user.setOnLineTime(new Date());
        User heihei = userIService.save(user);


        System.out.println(1);

    }
}

    
    
  