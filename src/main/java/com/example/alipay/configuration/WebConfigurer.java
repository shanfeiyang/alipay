
package com.example.alipay.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/19 14:30
 * @Version 1.0
 */
/**
 * 访问外部静态资源配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //addResourceHandler     是指你想在url请求的路径
        //addResourceLocations   是图片存放的真实路径
        registry.addResourceHandler("/images/**").addResourceLocations("file:F://qr/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}


