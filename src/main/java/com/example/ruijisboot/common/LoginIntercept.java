package com.example.ruijisboot.common;

//import com.baomidou.mybatisplus.extension.api.R;
import com.mysql.cj.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// 判断登录拦截器
@Component
public class LoginIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("userId");
        System.out.println(request);
        if (session != null && session.getAttribute("userId") != null){
            // 已登录
            return true;
        }else{
            response.sendError(403);
            request.setAttribute("msg","没有权限请先登陆");
            return false;
        }
    }
}
