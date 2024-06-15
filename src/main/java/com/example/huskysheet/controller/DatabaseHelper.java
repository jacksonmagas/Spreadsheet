package com.example.huskysheet.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class creates a database for the Server to store User information
 * so that returning users can log in, and makes sure Users can only register once.
 *
 * @author Katie w
 */

public class DatabaseHelper {
  private static final String URL = "jdbc:sqlite:users.db";

  public static Connection connect() {
    try {
      return DriverManager.getConnection(URL);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL," +
            "password TEXT NOT NULL)";

    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

