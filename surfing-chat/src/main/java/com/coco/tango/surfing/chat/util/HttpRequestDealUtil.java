package com.coco.tango.surfing.chat.util;

import com.coco.tango.surfing.chat.bean.SendServerVO;
import com.coco.tango.surfing.chat.constant.HttpConstant;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;

/**
 * Http 请求 处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Slf4j
public class HttpRequestDealUtil {

    public static String checkType(FullHttpRequest msg) {
        msg.retain();
        String url = msg.uri();
        HttpMethod method = msg.method();
        String meName = method.name();
        log.info("HttpRequestDealUtil receive http url for : {} method : {}", url, meName);
        if (url.equals(HttpConstant.UPLOARD_IMG_FILE) && meName.equals(HttpConstant.POST)) {
            return HttpConstant.UPLOARD_IMG_FILE;
        } else if (url.equals(HttpConstant.UPLOARD_YY_FILE) && meName.equals(HttpConstant.POST)) {
            return HttpConstant.UPLOARD_YY_FILE;
        } else {
            return HttpConstant.NOTFINDURI;
        }
    }

    public static SendServerVO getToken(FullHttpRequest msg) throws UnsupportedEncodingException {
        msg.retain();
        SendServerVO sendServerVO = new SendServerVO();
        String content = msg.content().toString(CharsetUtil.UTF_8);

        String[] stars = content.split("&");
        for (int i = 0, len = stars.length; i < len; i++) {
            String item = stars[i].toString();
            String firstType = item.substring(0, 5);
            String value = item.substring(6, item.length());
//            if (Constans.TOKEN.equals(firstType)) {
//                Token
//                sendServerVO.setToken(value);
//            } else if (Constans.VALUE.endsWith(firstType)) {
//                value
//                sendServerVO.setValue(value);
//            }
        }
        return sendServerVO;
    }


    /**
     * 图片根目录
     *
     * @return
     */
    public static String imgBasePath() {
        Environment environment = SpringContextUtil.getBean(Environment.class);
        return environment.getProperty(HttpConstant.FILE_UPLOAD_IMG_PATH);
    }

    /**
     * 语音根目录
     *
     * @return
     */
    public static String yyBasePath() {
        Environment environment = SpringContextUtil.getBean(Environment.class);
        return environment.getProperty(HttpConstant.FILE_UPLOAD_YY_PATH);
    }

}

    
    
  