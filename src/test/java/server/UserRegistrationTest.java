package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import Server.api.UserRegistrationService;
import Server.model.User;

public class UserRegistrationTest {

  private UserRegistrationService userRegistrationService;

  @BeforeEach
  public void setUp() {
    // Set up the service with mocked dependencies
    userRegistrationService = new UserRegistrationService();
  }

  @Test
  public void testRegisterNewUser() {
    // Define user registration details
    String username = "newuser";
    String password = "password123";
    String email = "newuser@gmail.com";

    // Register the new user
    boolean registrationResult = userRegistrationService.registerUser(username, password, email);

    // Verify the registration was successful
    assertTrue(registrationResult, "User should be registered successfully");

    // Verify the user can access the system (by logging in)
    User registeredUser = userRegistrationService.login(username, password);
    assertNotNull(registeredUser, "Registered user should be able to log in");
    assertEquals(username, registeredUser.getUsername(), "The logged in user should have the same username");
  }

  @Test
  public void testRegisterUserWithExistingUsername() {
    // Define user registration details
    String username = "existinguser";
    String password = "password123";
    String email = "existinguser@gmail.com";

    // First, register the user
    userRegistrationService.registerUser(username, password, email);

    // Attempt to register a user with the same username
    boolean registrationResult = userRegistrationService.registerUser(username, password, email);

    // Verify the registration failed
    assertFalse(registrationResult, "User registration should fail for existing username");
  }
}
