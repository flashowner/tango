package com.coco.tango.surfing.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.common.bean.HttpRestResult;
import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.constants.Constant;
import com.coco.tango.surfing.common.enums.ResultCode;
import com.coco.tango.surfing.common.redis.biz.UserTokenRedisCache;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class TangoFilter implements Filter {


    public static ThreadLocal<TangoUserDTO> currentUser = new ThreadLocal<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        currentUser.remove();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Environment environment = SpringContextUtil.getBean(Environment.class);
        boolean flag = false;
        String requestUrl = httpServletRequest.getRequestURI().replace(httpServletRequest.getContextPath(), "");
        String allowUrls = environment.getProperty(Constant.TANGO_FILTER_ARROW_URLS);
        if (!StringUtils.isEmpty(allowUrls)) {

            String[] allowUrlsArray = allowUrls.split(";");

            if (null != allowUrlsArray && allowUrlsArray.length >= 1) {
                for (String url : allowUrlsArray) {
                    if (requestUrl.contains(url)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            // token 校验
            String token = httpServletRequest.getHeader(Constant.LOGIN_USER_TOKEN);

            log.info("tangoFilter url : {} need token check : {}", requestUrl, token);

            if (StringUtils.isEmpty(token)) {
                // token 缺失，非法请求
                returnErrorData(httpServletResponse, "非法请求");
            } else {
                UserTokenRedisCache userTokenRedisCache = SpringContextUtil.getBean(UserTokenRedisCache.class);
                TangoUserDTO tangoUserDTO = userTokenRedisCache.getByToken(token);

                if (tangoUserDTO == null) {
                    //  用户信息 过期
                    returnErrorData(httpServletResponse, "用户信息过期,请重新登录");
                } else {
                    // 刷新token 过期时间
                    userTokenRedisCache.expire(token);
                    currentUser.set(tangoUserDTO);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }


    /**
     * 返回信息
     *
     * @param response
     * @param errorMsg
     */
    private void returnErrorData(HttpServletResponse response, String errorMsg) {
        HttpRestResult<String> result = new HttpRestResult<>();
        result.setSuccess(false);
        result.setCode(ResultCode.AUTH_ERROR.getCode());
        result.setMessage(errorMsg == null ? ResultCode.AUTH_ERROR.getMessage() : errorMsg);
        response.setCharacterEncoding(Constant.RESPONSE_CHARSET_ENCODING);
        response.setContentType(Constant.RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}