package com.coco.tango.surfing.match.biz.match;

import com.coco.tango.surfing.match.bean.vo.MatchPeopleVO;

import java.util.List;

/**
 * 匹配
 *
 * @author ckli01
 * @date 2019-07-11
 */
public interface MatchOtherManager {


    /**
     * 匹配用户
     *
     * @return
     */
    List<MatchPeopleVO> matchOthers();
}
