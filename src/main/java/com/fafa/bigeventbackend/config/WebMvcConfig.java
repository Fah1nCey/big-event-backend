package com.fafa.bigeventbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:处理跨域资源共享（CORS）的相关设置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 对所有请求路径都应用此CORS配置
        registry.addMapping("/**")
                // 允许浏览器发送跨域请求时携带认证信息（例如Cookie）
                .allowCredentials(true)
                // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                .allowedOriginPatterns("http://localhost:5173/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                // 允许所有的请求头
                .allowedHeaders("*")
                // 允许所有的响应头
                .exposedHeaders("*")
                // 浏览器可以缓存预检请求的结果1小时，从而减少后续的OPTIONS请求
                .maxAge(3600);
    }
}
