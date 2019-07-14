package com.coco.tango.surfing.match.biz.match.impl;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.filter.TangoFilter;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import com.coco.tango.surfing.core.service.test.UserQuestionAnswerIService;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import com.coco.tango.surfing.core.service.user.UserQusStateIService;
import com.coco.tango.surfing.match.bean.vo.MatchPeopleVO;
import com.coco.tango.surfing.match.biz.match.MatchOtherManager;
import com.coco.tango.surfing.match.constants.Constant;
import com.coco.tango.surfing.match.redis.UserMatchPageRedisCache;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 匹配
 *
 * @author ckli01
 * @date 2019-07-11
 */
@Service
public class MatchOtherManagerImpl implements MatchOtherManager {


    @Autowired
    private UserMatchPageRedisCache userMatchPageRedisCache;

    @Autowired
    private TangoUserIService tangoUserIService;

    @Autowired
    private UserQuestionAnswerIService answerIService;

    @Autowired
    private TestQuestionIService testQuestionIService;

    @Autowired
    private UserQusStateIService userQusStateIService;


    @Override
    public List<MatchPeopleVO> matchOthers() {
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();

        Long currentPage = Optional.ofNullable(userMatchPageRedisCache.hashGet(tangoUserDTO.getId())).orElse(0L);

        //  获取 总数
        int count = tangoUserIService.count();


        if (count > currentPage * Constant.MAX_MATCH_PAGE_SIZE) {
            // todo 匹配 人
            List<Long> userIds = tangoUserIService.listByPages(currentPage, Constant.MAX_MATCH_PAGE_SIZE);

            List<Long> sysQuesIds = testQuestionIService.systemQuestionIds();

            List<UserQuestionAnswer> list = answerIService.listByUserIdsAndQuesIds(userIds, sysQuesIds);

            Map<Long, List<UserQuestionAnswer>> map = list.stream().collect(Collectors.groupingBy(UserQuestionAnswer::getUserId));


            // todo    计算匹配度
            //   1. 用户匹配
            //


        } else {
            // todo 没有可以匹配的人了

        }
        userMatchPageRedisCache.hashSet(tangoUserDTO.getId(), currentPage + 1L);


        return null;
    }


}

    
    
  