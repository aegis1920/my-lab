package com.bingbong.cache.config;

import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class FilterConfig {

    // Filter를 통해 해당 경로 ETag 추가
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        Filter etagFilter = new ShallowEtagHeaderFilter();
        registrationBean.setFilter(etagFilter);
        registrationBean.addUrlPatterns("/home/etag/*");
        return registrationBean;
    }
}
