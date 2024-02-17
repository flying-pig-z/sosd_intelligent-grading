package com.flyingpig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class IntelligentGradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntelligentGradingApplication.class, args);
    }

}
