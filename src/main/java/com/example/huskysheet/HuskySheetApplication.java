package com.example.huskysheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(proxyBeanMethods = false)
@EnableWebSecurity

public class HuskySheetApplication {
  public static void main(String[] args) {
    SpringApplication.run(HuskySheetApplication.class, args);
  }
}