package com.coco.tango.surfing.user.biz.login.impl;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.redis.biz.UserTokenRedisCache;
import com.coco.tango.surfing.common.utils.RestUtils;
import com.coco.tango.surfing.common.utils.WrappedBeanCopier;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.dal.domain.user.WxTangoUser;
import com.coco.tango.surfing.core.dal.domain.user.WxUser;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import com.coco.tango.surfing.core.service.user.WxTangoUserIService;
import com.coco.tango.surfing.core.service.user.WxUserIService;
import com.coco.tango.surfing.user.bean.dto.WxLoginRes;
import com.coco.tango.surfing.user.bean.dto.WxLoginUserRes;
import com.coco.tango.surfing.user.bean.vo.TangoUserVO;
import com.coco.tango.surfing.user.biz.login.LoginManager;
import com.coco.tango.surfing.user.config.WxConfig;
import com.coco.tango.surfing.user.constant.LoginConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录 业务处理
 *
 * @author ckli01
 * @date 2019-07-07
 */
@Service
@Slf4j
public class LoginManagerImpl implements LoginManager {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxUserIService wxUserIService;

    @Autowired
    private WxTangoUserIService wxTangoUserIService;

    @Autowired
    private TangoUserIService tangoUserIService;


    @Autowired
    private UserTokenRedisCache userTokenRedisCache;

    @Override
    public WxLoginRes userFromWx(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", wxConfig.getAppId());
        map.put("secret", wxConfig.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String str = RestUtils.get(LoginConstant.WX_LOGIN_API, map, false);

        log.info("login : {}", str);

        WxLoginRes wxLoginRes = JSONObject.parseObject(str, WxLoginRes.class);

        log.info("login json : {}", JSONObject.toJSONString(wxLoginRes));
        return wxLoginRes;
    }


    /**
     * 先判断登录用户是否在系统中
     * 若没有先生成
     * 若存在直接返回用户信息
     *
     * @param wxLoginUserRes
     * @return
     */
    @Override
    public TangoUserVO userByOpenId(WxLoginUserRes wxLoginUserRes) {
        WxUser wxUser = wxUserIService.findByOpenId(wxLoginUserRes.getOpenId());
        if (null == wxUser) {
            // 不存在，代表首次登录, 插入微信用户数据
            wxUser = new WxUser();
            wxUser.setAppId(wxConfig.getAppId());
            wxUser.setOpenId(wxLoginUserRes.getOpenId());
            wxUserIService.save(wxUser);
        }
        // 获取 微信用户  tango 用户关联信息
        WxTangoUser wxTangoUser = wxTangoUserIService.findByWxUserId(wxUser.getId());
        TangoUser tangoUser;
        if (null == wxTangoUser) {
            // 不存在，代表首次登录, 插入 tango用户 数据 生成 用户关联关系
            tangoUser = new TangoUser();
            tangoUser.setName(wxLoginUserRes.getNickName());
            tangoUser.setSex(Integer.valueOf(wxLoginUserRes.getGender()));
            tangoUser.setAvatarUrl(wxLoginUserRes.getAvatarUrl());
            tangoUserIService.save(tangoUser);

            wxTangoUser = new WxTangoUser();

            wxTangoUser.setTangoUserId(wxUser.getId());
            wxTangoUser.setTangoUserId(tangoUser.getId());

            wxTangoUserIService.save(wxTangoUser);
        } else {
            // 获取 Tango 用户信息
            tangoUser = tangoUserIService.getById(wxTangoUser.getTangoUserId());
        }

        // 构建返回给前端 用户信息
        TangoUserVO tangoUserVO = WrappedBeanCopier.copyProperties(tangoUser, TangoUserVO.class);

        tangoUserVO.setAvatarUrl(tangoUser.getAvatarUrl());
        tangoUserVO.setProvince(wxLoginUserRes.getProvince());
        tangoUserVO.setProvince(wxLoginUserRes.getCity());
        tangoUserVO.setCountry(wxLoginUserRes.getCountry());
        tangoUserVO.setId(null);

        String token = DigestUtils.md5Hex(tangoUser.getCode());
        // token 放在系统中保存一小时
        userTokenRedisCache.set(token, WrappedBeanCopier.copyProperties(tangoUser, TangoUserDTO.class));

        tangoUserVO.setToken(token);

        return tangoUserVO;
    }


}

    
    
  