package com.example.huskysheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * The main application class for HuskySheet.
 * Configures and initializes the Spring Boot application with security enabled.
 *
 * @author Julia Ouritskaya
 */
@SpringBootApplication(proxyBeanMethods = false)
@EnableWebSecurity
public class HuskySheetApplication {

  /**
   * The main method to run the Spring Boot application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(HuskySheetApplication.class, args);
  }
}