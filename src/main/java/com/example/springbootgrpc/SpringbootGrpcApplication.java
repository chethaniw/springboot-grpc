package com.example.springbootgrpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
//@EnableIntegration
public class SpringbootGrpcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGrpcApplication.class, args);
    }

}
