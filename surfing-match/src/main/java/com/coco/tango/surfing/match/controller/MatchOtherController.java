package com.coco.tango.surfing.match.controller;

import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.controller.AbstractBaseController;
import com.coco.tango.surfing.match.bean.vo.MatchPeopleVO;
import com.coco.tango.surfing.match.biz.match.MatchOtherManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 匹配
 *
 * @author ckli01
 * @date 2019-07-11
 */
@RestController
@RequestMapping("/match")
public class MatchOtherController extends AbstractBaseController {


    @Autowired
    private MatchOtherManager matchOtherManager;


    @GetMapping("/others")
    public HttpRestResult<List<MatchPeopleVO>> matchOthers() {
        return responseOK(matchOtherManager.matchOthers());
    }

}

    
    
  