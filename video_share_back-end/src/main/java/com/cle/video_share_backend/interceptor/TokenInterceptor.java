package com.cle.video_share_backend.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.cle.video_share_backend.pojo.User;
import com.cle.video_share_backend.utils.JWTUtils;
import com.cle.video_share_backend.utils.UserThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");
        if(authorization==null){
            //处理未登录的逻辑
        }else {
            User user = new User();
            user.setId( Long.valueOf(JWTUtils.getClaimByName(authorization, "id").asString()) );
             user.setName(JWTUtils.getClaimByName(authorization, "name").asString());
             user.setAvatar(JWTUtils.getClaimByName(authorization,"avatar").asString());
             user.setEmail(JWTUtils.getClaimByName(authorization,"email").asString());
            UserThreadLocal.set(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}