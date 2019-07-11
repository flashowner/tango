package com.coco.tango.surfing.common.config;

import com.coco.tango.surfing.common.filter.TangoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 过滤器配置
 *
 * @author ckli01
 * @date 2019-07-10
 */
@Component
public class FilterConfig {


    @Bean
    public FilterRegistrationBean tangoFilter() {
        FilterRegistrationBean<TangoFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TangoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("tangoFilter");
        return registration;
    }


}

    
    
  