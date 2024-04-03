package com.example.ruijisboot.config;

import com.example.ruijisboot.common.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    private LoginIntercept loginIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginIntercept).
                addPathPatterns("/**").    // 拦截所有 url
                excludePathPatterns("/user/login"). //不拦截登录注册接口
                excludePathPatterns("/user/reg").
                excludePathPatterns("/login.html").
                excludePathPatterns("/reg.html").
                excludePathPatterns("/**/*.js").
                excludePathPatterns("/**/*.css").
                excludePathPatterns("/**/*.png").
                excludePathPatterns("/**/*.jpg").
                excludePathPatterns("/doc.html").
                excludePathPatterns("/favicon.ico").
                excludePathPatterns("/webjars/**").
                excludePathPatterns("/swagger-resources").
                excludePathPatterns("/**/api-docs").
                excludePathPatterns("upload/**");
    }
}
