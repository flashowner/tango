package com.coco.tango.surfing.core.dal.repository.user;

import com.coco.tango.surfing.core.dal.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 用户
 *
 * @author ckli01
 * @date 2019-06-27
 */
public interface UserRepository extends MongoRepository<User, String> {
}
