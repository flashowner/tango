package com.coco.tango.surfing.core.service.user.impl;

import com.coco.tango.surfing.core.dal.domain.user.User;
import com.coco.tango.surfing.core.dal.repository.user.UserRepository;
import com.coco.tango.surfing.core.service.user.UserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author ckli01
 * @date 2019-06-27
 */
@Service
public class UserIServiceImpl implements UserIService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findByCode(String code) throws Exception {
        return userRepository.findById(code).orElseThrow(() -> new Exception("code not exist"));
    }
}

    
    
  