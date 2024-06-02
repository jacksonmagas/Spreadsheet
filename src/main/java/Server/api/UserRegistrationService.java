package Server.api;

import Server.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationService {
  private Map<String, User> users = new HashMap<>();

  public boolean registerUser(String username, String password, String email) {
    if (users.containsKey(username)) {
      return false; // User already exists
    }
    User newUser = new User(username, password, email);
    users.put(username, newUser);
    return true;
  }

  public User login(String username, String password) {
    User user = users.get(username);
    if (user != null && user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }
}
