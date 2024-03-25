package com.example.ruijisboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@ServletComponentScan
@Slf4j
public class RuijisbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijisbootApplication.class, args);
        log.info("服务已开启");
    }

}
