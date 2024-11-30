package com.hyw.platform.web.syswebconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebPermConfig implements WebMvcConfigurer {

    @Autowired
    private WebApiInterceptor webApiInterceptor;

    /**
     * 用于注册拦截器，可以通过该方法添加自定义的拦截器。
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webApiInterceptor);
    }

    /**
     * 配置静态资源处理器 静态资源的位置和路径映射
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}