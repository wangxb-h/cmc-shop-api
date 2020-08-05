package com.fh.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的域名
        String yuming = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", yuming);
        //当客户端修改了头信息     发起两个请求  第一个是预请求   options （是否允许修改头信息）     发起真正的请求
        String method = request.getMethod();
        if (method.equalsIgnoreCase("options")){
            //允许修改头信息  添加一个name属性
            response.setHeader("Access-Control-Allow-Headers","token");
            return false;
        }
        return true;
    }
}
