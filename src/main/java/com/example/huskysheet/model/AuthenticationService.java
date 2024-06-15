package com.example.huskysheet.model;

import com.example.huskysheet.controller.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class uses the database and registers users into that database, and also
 * makes sure a user can not register twice. It also authenticates users when they
 * try and login. It pulls data from the database and matches it with the data given
 * by the user.
 *
 * @author Katie w
 */

public class AuthenticationService {

  public AuthenticationService() {
    DatabaseHelper.createTable();
  }

  public boolean register(String username, String password) {
    if (isUserExists(username)) {
      System.out.println("User already exists");
      return false;
    }

    String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
    try (Connection conn = DatabaseHelper.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, username);
      pstmt.setString(2, password);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  private boolean isUserExists(String username) {
    String sql = "SELECT username FROM users WHERE username = ?";
    try (Connection conn = DatabaseHelper.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean authenticate(String username, String password) {
    String sql = "SELECT password FROM users WHERE username = ?";

    try (Connection conn = DatabaseHelper.connect();
         PreparedStatement prepared = conn.prepareStatement(sql)) {

      prepared.setString(1, username);
      ResultSet rs = prepared.executeQuery();

      // compares pass
      if (rs.next()) {
        String storedPassword = rs.getString("password");
        return storedPassword.equals(password); //stores pass
      } else {
        System.out.println("User not found.");
        return false; // User not found
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
