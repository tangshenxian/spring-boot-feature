package com.shenxian.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.util.Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.IOException;


/**
 * @author: shenxian
 * @date: 2022/3/21 15:54
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class RemoveDruidAdConfig {

    /**
     * 出去druid监控页面底部的广告
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> removeDruidAdFilterRegistrationBean() throws IOException {
        final String filePath = "support/http/resources/js/common.js";
        // 获取common.js内容
        String text = Utils.readFromResource(filePath);
        // 屏蔽 this.buildFooter(); 直接替换为空字符串,让js没机会调用
        final String newJs = text.replace("this.buildFooter();", "");

        // 创建filter进行过滤
        Filter filter = (servletRequest, servletResponse, filterChain) -> {
            filterChain.doFilter(servletRequest, servletResponse);
            // 重置缓冲区，响应头不会被重置
            servletResponse.resetBuffer();
            servletResponse.getWriter().write(newJs);
        };
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.addUrlPatterns("/druid/js/common.js");
        return bean;
    }

}
