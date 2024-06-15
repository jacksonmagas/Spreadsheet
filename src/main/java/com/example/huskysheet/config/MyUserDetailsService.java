package com.example.huskysheet.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class keeps tracks of user information for the UserRepository. It is able
 * to load an existing User by the username as well as registers new users if
 * they dont already exist.
 *
 * @author Katie w
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

  @Autowired
  public MyUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
  }

  public void register(String username, String password) throws Exception {
    logger.debug("Attempting to register user: {}", username);
    UserEntity existingUser = userRepository.findByUsername(username);
    if (existingUser.getUsername() == username) {
      logger.debug("User already exists: {}", username);
      throw new Exception("User already exists");
    }
    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEnabled(true); // ensure the user is enabled
    user.setRole("USER"); // set default role
    userRepository.save(user);
    logger.debug("User registered successfully: {}", username);
  }
}

