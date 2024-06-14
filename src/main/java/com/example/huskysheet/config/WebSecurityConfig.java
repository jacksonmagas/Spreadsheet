package com.example.huskysheet.config;

import java.util.List;
import javafx.util.Pair;
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
    String fileName = "src/main/resources/UsernamePasswords.txt";
    List<Pair<String, String>> userDetails = userDetailsFromFile(fileName);
    UserDetails[] users = userDetails.stream()
        .map(p -> User.withDefaultPasswordEncoder()
            .username(p.getKey())
            .password(p.getValue())
            .roles("USER")
            .build()).toArray();
    return new InMemoryUserDetailsManager(users);
  }

  private List<Pair<String, String>> userDetailsFromFile(String fileName) {
    //TODO read from file
    return List.of(new Pair<>("alice", "ert*hdu4GGwkw89"), new Pair<>("bob", "2V56$*BBBB1}mkrl"), new Pair<>("admin", "admin123"));
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
