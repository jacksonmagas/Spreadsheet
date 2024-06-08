package Server.model;

import Client.Model.Utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
  private String username;
  private String password;
  private String email;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }


  public User getUserInfo(String username) {
    User user = null;
    try (Connection connection = DatabaseUtil.getConnection()) {
      // Finds the user by Username (? is a placeholder)
      String query = "SELECT * FROM users WHERE username = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        // Makes the index for username = 1
        preparedStatement.setString(1, username);


        ResultSet resultSet = preparedStatement.executeQuery();

        // Finds the user info if the User is found
        if (resultSet.next()) {
          String password = resultSet.getString("password");
          String email = resultSet.getString("email");
          user = new User(username, password, email);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }


  public void registerUserSpreadsheet(String username, String password, String email) {
    String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

    try (Connection connection = DatabaseUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      // Check if the user already exists
      if (getUserInfo(username) != null) {
        System.out.println("Username already exists!");
      }

      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, email);
      preparedStatement.executeUpdate();
      System.out.println("User " + username + " has been added to the database!");

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Sql Exception Error");
    }
  }

}