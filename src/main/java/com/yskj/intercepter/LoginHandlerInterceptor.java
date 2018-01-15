package com.yskj.intercepter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 *
 * @author zhouchuang
 * @create 2018-01-15 22:46
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    String NO_INTERCEPTOR_PATH = ".*/((login)|(reg)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";    //不对匹配该值的访问路径拦截（正则）

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        String path = request.getServletPath();
        if (path.matches(NO_INTERCEPTOR_PATH)) {    //匹配正则表达式的不拦截
            return true;
        } else {    //不匹配的进行处理
            try {
                if (request.getSession().getAttribute("userInfo") == null) { //session中是否存在用户信息，不存在则是未登录状态
                    response.sendRedirect(request.getContextPath() + "/login");
                    return false;
                }
            } catch (IOException e) {
                response.sendRedirect(request.getContextPath() + "/login");
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
