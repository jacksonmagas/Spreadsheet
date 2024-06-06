/*
package Server.api;

import Server.model.Publisher;
import Server.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationService {
  private Map<String, User> users = new HashMap<>();
  private Map<String, Publisher> publishers = new HashMap<>();

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

  public boolean registerAsPublisher(String username) {
    User user = users.get(username);
    if (user == null) {
      return false; // User does not exist
    }
    if (publishers.containsKey(username)) {
      return false; // User is already a publisher
    }
    Publisher newPublisher = new Publisher(user.getUsername(), user.getEmail());
    publishers.put(username, newPublisher);
    return true;
  }

  public Publisher getPublisher(String username) {
    return publishers.get(username);
  }

  public User getUser(String username) {
    return users.get(username);
  }
}
*/