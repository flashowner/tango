package com.coco.tango.surfing.user.biz.login;

import com.coco.tango.surfing.user.bean.dto.WxLoginRes;
import com.coco.tango.surfing.user.bean.dto.WxLoginUserRes;
import com.coco.tango.surfing.user.bean.vo.TangoUserVO;

/**
 * 登录 处理
 *
 * @author ckli01
 * @date 2019-07-07
 */
public interface LoginManager {


    /**
     * 从 微信 服务器 获取 用户信息
     *
     * @param code
     */
    WxLoginRes userFromWx(String code);


    /**
     * 用户 信息 校验 以及 生成
     * @param wxLoginUserRes
     * @return
     */
    TangoUserVO userByOpenId(WxLoginUserRes wxLoginUserRes);
}
