package com.fafa.bigeventbackend.interceptors;
import com.fafa.bigeventbackend.utils.JwtUtil;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 配置拦截器对象
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 请求拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 关键改动：优先处理 OPTIONS 请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        // 验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            // 响应状态码为401
            response.setStatus(401);
            throw new Exception("token验证失败");
//            return false;
        }
    }

    // 响应拦截器
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除ThreadLocal中的业务数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
