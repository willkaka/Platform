package com.hyw.platform;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.hyw.platform.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("System starts successfully!");
    }
}
