package com.coco.tango.surfing.match.biz.match.impl;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.exception.ServiceException;
import com.coco.tango.surfing.common.filter.TangoFilter;
import com.coco.tango.surfing.core.dal.domain.test.UserQuestionAnswer;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.service.test.TestQuestionIService;
import com.coco.tango.surfing.core.service.test.UserQuestionAnswerIService;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import com.coco.tango.surfing.core.service.user.UserQusStateIService;
import com.coco.tango.surfing.match.bean.vo.MatchPeopleVO;
import com.coco.tango.surfing.match.biz.match.MatchOtherManager;
import com.coco.tango.surfing.match.constants.Constant;
import com.coco.tango.surfing.match.redis.UserMatchOtherRedisCache;
import com.coco.tango.surfing.match.redis.UserMatchPageRedisCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
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


    @Autowired
    private UserMatchOtherRedisCache userMatchOtherRedisCache;


    @Override
    public MatchPeopleVO matchOthers() throws Exception {
        TangoUserDTO tangoUserDTO = TangoFilter.currentUser.get();
        MatchPeopleVO matchPeople = userMatchOtherRedisCache.popOne(tangoUserDTO.getId());

        if (matchPeople != null) {
            return matchPeople;
        }

        // todo 去除测试
//        Integer currentPage = Optional.ofNullable(userMatchPageRedisCache.hashGet(tangoUserDTO.getId())).orElse(0);
        Integer currentPage = 0;

        //  获取 总数
        int count = tangoUserIService.count();

        if (count > currentPage * Constant.MAX_MATCH_PAGE_SIZE) {
            //  匹配 人
            List<TangoUser> users = tangoUserIService.listByPages(currentPage, Constant.MAX_MATCH_PAGE_SIZE);

            List<Long> userIds = users.stream().map(TangoUser::getId).collect(Collectors.toList());

            // 获取需要比较的题目
            List<Long> sysQuesIds = testQuestionIService.systemQuestionIds();

            // 批量获取待比较用户的答题记录
            List<UserQuestionAnswer> list = answerIService.listByUserIdsAndQuesIds(userIds, sysQuesIds);

            // 当前用户 系统答题
            List<UserQuestionAnswer> currentAnswer = answerIService.listByUserIdsAndQuesIds(Lists.newArrayList(tangoUserDTO.getId()), sysQuesIds);

            if (!CollectionUtils.isEmpty(list)) {
                // 根据用户 分组 答题记录
                Map<Long, List<UserQuestionAnswer>> map = list.stream().collect(Collectors.groupingBy(UserQuestionAnswer::getUserId));
                Map<Long, UserQuestionAnswer> currentAnswerMap = currentAnswer.stream().collect(Collectors.toMap(UserQuestionAnswer::getQuestionId, Function.identity()));

                Map<Long, Double> matchDegreeMap = Maps.newTreeMap();

                map.forEach((k, v) -> {
                    // 只有完成所有题目的人参与 匹配
                    if (v.size() == currentAnswer.size()) {
                        AtomicReference<Double> score = new AtomicReference<>(0D);
                        Map<Long, UserQuestionAnswer> vMap = v.stream().collect(Collectors.toMap(UserQuestionAnswer::getQuestionId, Function.identity()));

                        vMap.forEach((a, b) -> {
                            // 每道题目 根据 分数 取 绝对值
                            UserQuestionAnswer current = currentAnswerMap.get(a);

                            Double currentScore = current.getScore() == null ? Double.valueOf(current.getAnswer()) : current.getScore();
                            Double bScore = b.getScore() == null ? Double.valueOf(b.getAnswer()) : b.getScore();

                            score.updateAndGet(v1 -> v1 + Math.abs(currentScore - bScore));
                        });

                        double maxScore = sysQuesIds.size() * Constant.MAX_SCORE_PER_QUESTION;

                        // 计算匹配度
                        double matchDegree = (1 - score.get() / maxScore) * 100;

                        // 满足最低匹配度的匹配者记录下来 取两位小数
                        if (matchDegree >= Constant.MIN_MATCH_DEGREE) {
                            BigDecimal b = new BigDecimal(matchDegree);
                            matchDegreeMap.put(k, b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }

                    }
                });
                // 根据匹配度排序
                if (!CollectionUtils.isEmpty(matchDegreeMap)) {
//                    // 将map.entrySet()转换成list
//                    List<Map.Entry<Long, Double>> list1 = Lists.newArrayList(matchDegreeMap.entrySet());
//                    // 通过比较器来实现排序
//                    list1.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

                    List<MatchPeopleVO> matchPeopleVOS = users.stream().filter(tangoUser -> matchDegreeMap.containsKey(tangoUser.getId())
                            && !tangoUser.getId().equals(tangoUserDTO.getId()))
                            .map(tangoUser -> {
                                MatchPeopleVO matchPeopleVO = new MatchPeopleVO();
                                matchPeopleVO.setAvatarUrl(tangoUser.getAvatarUrl());
                                matchPeopleVO.setCode(tangoUser.getCode());
//                                matchPeopleVO.setDescription(tangoUser.get);
                                matchPeopleVO.setMatch(matchDegreeMap.get(tangoUser.getId()));
                                matchPeopleVO.setName(tangoUser.getName());
                                matchPeopleVO.setSex(tangoUser.getSex());

                                return matchPeopleVO;
                            }).collect(Collectors.toList());
                    matchPeople = matchPeopleVOS.remove(0);
                    userMatchOtherRedisCache.pushAll(tangoUserDTO.getId(),matchPeopleVOS);
                }
            }
            userMatchPageRedisCache.hashSet(tangoUserDTO.getId(), currentPage + 1);
        } else {
            throw  new ServiceException("没有匹配到用户");
        }

        return matchPeople;
    }


}

    
    
  