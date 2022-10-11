package com.wen.gradua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@SpringBootApplication
@EnableScheduling //允许进行定时计划
public class GraduaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraduaApplication.class, args);
    }
}
