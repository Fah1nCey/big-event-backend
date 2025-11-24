package com.fafa.bigeventbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fafa.bigeventbackend.mapper")
public class BigEventBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigEventBackendApplication.class, args);
    }

}
