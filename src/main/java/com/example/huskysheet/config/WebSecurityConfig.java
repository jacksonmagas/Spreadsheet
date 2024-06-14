package com.example.huskysheet.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
  public UserDetailsService userDetailsService() throws FileNotFoundException {
    String fileName = "src/main/resources/UsernamePasswords.txt";
    List<Pair<String, String>> userDetails = userDetailsFromFile(fileName);
    UserDetails[] users = userDetails.stream()
        .map(p -> User.withDefaultPasswordEncoder()
            .username(p.getKey())
            .password(p.getValue())
            .roles("USER")
            .build()).toArray(UserDetails[]::new);
    return new InMemoryUserDetailsManager(users);
  }

  /**
   * Read the usernames and passwords from file
   * @author Jackson Magas
   * @param fileName
   * @return
   */
  private List<Pair<String, String>> userDetailsFromFile(String fileName)
      throws FileNotFoundException {

    List<Pair<String, String>> userDetails = new ArrayList<>();
    Scanner sc = new Scanner(new File(fileName));
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String username = line.split(":")[0];
      String password = line.split(":")[1];
      userDetails.add(new Pair<>(username, password));
    }
    return userDetails;
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
