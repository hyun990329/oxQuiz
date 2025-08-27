package com.my.oxQuiz.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // localhost:8080/user/login -> PostMapping 으로 넘어감
        HttpSession session = request.getSession();
        Object loginId = session.getAttribute("loginNo");
        if (ObjectUtils.isEmpty(loginId)) {
            response.sendRedirect("/user/login");
        } else {
            return true;
        }
        return false;
    }
}
