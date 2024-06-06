package com.example.huskysheet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin123")
            .roles("USER")
            .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection in Spring Security 6.1+
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/api/v1/register").permitAll()
                            .requestMatchers("/api/v1/getPublishers").authenticated()
                            .requestMatchers("/api/v1/createSheet").authenticated()
                            .requestMatchers("/api/v1/getSheets").authenticated()
                            .requestMatchers("/api/v1/deleteSheet").authenticated()
                            .requestMatchers("/api/v1/getUpdatesForSubscription").authenticated()
                            .requestMatchers("/api/v1/getUpdatesForPublished").authenticated()
                            .requestMatchers("/api/v1/updatePublished").authenticated()
                            .requestMatchers("/api/v1/updateSubscription").authenticated()
                            .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

    return http.build();
  }
}
