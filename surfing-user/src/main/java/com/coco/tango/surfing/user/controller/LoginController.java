package com.coco.tango.surfing.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.controller.AbstractBaseController;
import com.coco.tango.surfing.user.bean.dto.WxLoginRes;
import com.coco.tango.surfing.user.bean.dto.WxLoginUserRes;
import com.coco.tango.surfing.user.bean.vo.TangoUserVO;
import com.coco.tango.surfing.user.biz.login.LoginManager;
import com.coco.tango.surfing.user.utils.AesCbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录服务
 *
 * @author ckli01
 * @date 2019-07-07
 */
@RestController
@RequestMapping("/login")
public class LoginController extends AbstractBaseController {


    @Autowired
    private LoginManager loginService;


    /**
     * 微信登录
     *
     * @param code          凭证
     * @param encryptedData 用户数据
     * @param iv            用户数据
     * @return map
     */
    @GetMapping("/wx")
    public HttpRestResult<TangoUserVO> wxLogin(String code, String encryptedData, String iv) throws Exception {
        WxLoginRes wxLoginRes = loginService.userFromWx(code);
        String decrypts = AesCbcUtil.decrypt(encryptedData, wxLoginRes.getSession_key(), iv, "utf-8");//解密
        WxLoginUserRes wxLoginUserRes = JSONObject.parseObject(decrypts, WxLoginUserRes.class);
        return responseOK(loginService.userByOpenId(wxLoginUserRes));
    }


}

    
    
  