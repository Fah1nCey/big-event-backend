package com.fafa.bigeventbackend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class JwtUtilTest {

    @Test
    void genToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("username", "admin");
        String token = JwtUtil.genToken(map);
        System.out.println(token);
    }

    @Test
    void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluIn0sImV4cCI6MTc2MzU3NzA0MX0.8loBjAu7hu6rkGI-QiuV_jWrFHnSQTMALba-2Y-npuw";
        Map<String, Object> stringObjectMap = JwtUtil.parseToken(token);
        System.out.println(stringObjectMap);
    }
}