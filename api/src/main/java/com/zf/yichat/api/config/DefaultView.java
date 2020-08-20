package com.zf.yichat.api.config;

import com.zf.yichat.api.config.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class DefaultView implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("/controller/");
        //registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }

    @Bean
    public AuthInterceptor myInterceptor() {
        return new AuthInterceptor();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/controller","/webjars/layui/2.3.0/css/font/iconfont.woff");
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }


}